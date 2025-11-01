import { defineStore } from "pinia";
import { StorageUtils } from "@/utils";

export const useUserStore = defineStore("user", {
    state: () => ({
        token: "",
        user: {}
    }),
    actions: {
        setToken(token) {
            this.token = token;
            StorageUtils.setToken(token);
        },
        setUser(user) {
            this.user = user;
        },
        clearUser() {
            this.user = {};
        }
    }
});