import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
    state: () => ({
        name: "zhangsan",
        age: 18,
        address: "北京市",
    }),
    getters: {
        getName(state) {
            return state.name + "!!!";
        }
    },
    actions: {
        updateName(name) {
            this.name = name;
        }
    }
});