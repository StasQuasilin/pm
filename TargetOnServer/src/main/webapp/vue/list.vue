let list = {
    data:function () {
        return {
            api:{},
            items: {}
        }
    },
    methods:{
        remove:function(id){
            if (this.api.remove && id){
                loadModal(this.api.remove, {id:id})
            } else {
                console.warn(this.$el.id + '.api.remove link required!');
            }
        },
        edit:function(id, params){
            if (this.api.edit){
                let data = Object.assign({}, params);
                if (id){
                    data.id = id;
                }
                loadModal(this.api.edit, data)
            }
        },
        handler:function(items){
            console.log(items);

            if(items) {
                for (let a in items.add) {
                    if (items.add.hasOwnProperty(a)) {
                        let item = items.add[a];
                        this.update(item);
                    }
                }
                if (items.update){
                    this.update(items.update);
                }
                if (items.remove){

                    Vue.delete(this.items, items.remove.id);
                }
                this.removeOverLimit();
            }
            if (typeof this.afterHandle != "undefined"){
                this.afterHandle();
            }
        },
        update:function(item){
            Vue.set(this.items, item.id, item);
        },
        removeOverLimit:function(){
            if (this.limit > 0){
                let items = this.getItems();
                if (items.length > this.limit){
                    let item = items[items.length - 1];
                    this.remove(item.id);
                    this.removeOverLimit();
                }
            }
        },
        itemsCount:function(){
            return Object.keys(this.items).length;
        },
        getItems:function () {
            let values = Object.values(this.items);
            values.sort(this.sort);
            return values;
        },
        sort:function(a, b){
            return a - b;
        }
    }
};