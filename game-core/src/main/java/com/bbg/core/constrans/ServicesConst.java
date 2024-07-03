package com.bbg.core.constrans;

import lombok.Getter;

@Getter
public enum ServicesConst {
    ADMIN_SERVER("admin-server"),
    BOX_APP_SERVER("box-app-server"),
    SCHEDULER_APP("scheduler-server"),
    GATEWAY_SERVER("gateway-server"),
    SOCKET_SERVER("socket-server"),
    GAME_THIRD_SERVER("game-third-server");

    private final String serviceName;

    ServicesConst(String serviceName) {
        this.serviceName = serviceName;
    }

}
