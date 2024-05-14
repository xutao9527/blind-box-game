package com.bbg.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBattleRoom;
import lombok.Builder;
import lombok.Data;
import java.nio.charset.StandardCharsets;

public class FairFactory {
    private final static Snowflake snowflake = IdUtil.getSnowflake();

    /**
     * 生产公平性密钥对象
     */
    public static FairEntity build() {
        String secretHash = DigestUtil.sha256Hex(snowflake.nextIdStr().getBytes());
        String secretSalt = DigestUtil.sha256Hex(snowflake.nextIdStr().getBytes());
        String publicHash = DigestUtil.sha256Hex(byteMerger(HexUtil.decodeHex(secretHash), HexUtil.decodeHex(secretSalt)));
        String clientSeed = DigestUtil.sha256Hex(snowflake.nextIdStr().getBytes());
        return FairEntity.builder().secretHash(secretHash).secretSalt(secretSalt).publicHash(publicHash).clientSeed(clientSeed).build();
    }

    /**
     * 生产公平性密钥对象
     */
    public static FairEntity build(BizUser bizUser) {
        return FairEntity.builder()
                .clientSeed(bizUser.getCsgoUserInfo().getClientSeed())
                .publicHash(bizUser.getCsgoUserInfo().getPublicHash())
                .secretSalt(bizUser.getCsgoUserInfo().getSecretSalt())
                .secretHash(bizUser.getCsgoUserInfo().getSecretHash()).build();
    }

    /**
     * 生产公平性密钥对象
     */
    public static FairEntity build(CsgoBattleRoom csgoBattleRoom) {
        return FairEntity.builder()
                .clientSeed(csgoBattleRoom.getClientSeed())
                .publicHash(csgoBattleRoom.getPublicHash())
                .secretSalt(csgoBattleRoom.getSecretSalt())
                .secretHash(csgoBattleRoom.getSecretHash()).build();
    }

    /**
     * 辅助方法：拼接两个字节数组
     */
    private static byte[] byteMerger(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    @Data
    @Builder
    public static class FairEntity {
        public static final int SEED_MIN_ROLL = 1;
        public static final int SEED_MAX_ROLL = 1000000;
        private String secretHash;
        private String secretSalt;
        private String publicHash;
        private String clientSeed;

        public int roll(int roundNumber) {
            String data = clientSeed + "-" + roundNumber;
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] digestBytes = DigestUtil.hmac(HmacAlgorithm.HmacSHA512, HexUtil.decodeHex(secretHash)).digest(dataBytes);
            String digestHex = HexUtil.encodeHexStr(digestBytes).substring(0, 7);
            return Integer.parseInt(digestHex, 16) % SEED_MAX_ROLL + SEED_MIN_ROLL;
        }
    }

    public static void main(String[] args) {
        FairEntity fairEntity = FairFactory.build();
        fairEntity = FairEntity.builder()
                .secretHash("629705d55cc386d773f44aa544560a4db63df0704faabe08b95a56fe3a791d6b")
                .secretSalt("b3483496927f17679d236879ad1dc8cc1f4f237865a63e055abddd940302e553")
                .publicHash("ffb41ae1e0b3fa47a25b68bd2eb8fb53b2efa67e426451cfe9324bf405294268")
                .clientSeed("e49153671182b77e21830e8118bd55a48613a16a7db0014d0e49da1f42bfb31c").build();
        System.out.println(fairEntity.roll(1));
        System.out.println(fairEntity.roll(2));
        System.out.println(fairEntity.roll(3));
        System.out.println(fairEntity.roll(4));
        System.out.println(fairEntity.roll(5));
    }

}
