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
                    @focus="input"
                    :value="date"
            ></v-text-field>
            <v-date-picker style="position:fixed;z-index: 999;" v-if="showPicker" v-model="date"></v-date-picker>
        </div>
     `
});