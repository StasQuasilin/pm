ed = {
    data:function () {
        return {
            api:{},
            item:{}
        }
    },
    methods:{
        save:function () {
            PostApi(this.api.save, this.item, function (a) {
                if (a.status === 'success'){
                    if (a.result){
                        saveModal(a.result);
                    }
                    closeModal();
                } else {
                    console.log(a);
                }
            })
        }
    }
};