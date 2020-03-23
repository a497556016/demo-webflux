Vue.component("m-date-picker", {
    props: {
        label: String,
        placeholder: String,
        value: String
    },
    model: {
        prop: 'value',
        event: 'change'
    },
    data(){
        return {
            showPicker: false,
            date: this.value
        }
    },
    watch: {
        date(v){
            this.showPicker = false;
            this.$emit('change', v);
        }
    },
    mounted(){
        console.log(this.showPicker)
    },
    methods: {
        input(){
            this.showPicker = true;
        }
    },
    template: `
        <div>
            <v-text-field
                    :label="label"
                    :placeholder="placeholder"
                    readonly
                    @click="input"
                    v-model="date"
                     :clearable="true"
            ></v-text-field>
            <v-date-picker style="position:fixed;z-index: 999;" v-if="showPicker" v-model="date"></v-date-picker>
            <div v-if="showPicker" style="position: fixed;z-index: 998;top: 0;bottom: 0;left: 0;right: 0;" @click="showPicker = false"></div>
        </div>
     `
});