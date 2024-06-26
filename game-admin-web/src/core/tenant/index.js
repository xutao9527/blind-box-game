import {useUserStore} from "@/store/userStore.js";

const store = useUserStore()
const isSuperTenant = ref(false);
const checkSuperTenant = async () => {
    isSuperTenant.value = (await store.getUser).superTenant
}
await checkSuperTenant()
export default isSuperTenant
