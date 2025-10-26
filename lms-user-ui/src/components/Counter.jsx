import { defineComponent, ref } from "vue";
import { ElButton } from "element-plus";
export default defineComponent({
    name: "Counter",
    setup() {
        const count = ref(0);
        const increment = () => {
            count.value++;
        };
        return () => (
            <div>
                <h2>计数器:</h2>
                <p>当前计数:{count.value}</p>
                <ElButton type="primary" onClick={increment}>+</ElButton>
            </div>
        )
    }
})