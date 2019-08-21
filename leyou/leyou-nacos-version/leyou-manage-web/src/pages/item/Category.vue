<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>
  export default {
    name: "category",
    data() {
      return {
        isEdit:true
      }
    },
    methods: {
      handleAdd(node) {
          this.$http.post("/item/category",node).then(result =>{
            console.log(result);
          }).catch(err =>{
              this.$message.error("添加商品类别失败");
            })
      },
      handleEdit(id, name) {
        this.$http.put("/item/category",{
          id:id,
          name:name
        }).then(result =>{
          console.log(result);
        }).catch(err =>{
          this.$message.error("修改商品类别失败");
        })
      },
      handleDelete(id) {
        this.$http.delete("/item/category/" + id)
          .then(result =>{
            console.log(result)
          }).catch(err =>{
              this.$message.error("删除商品类别失败");
            })
      },
      handleClick(node) {

      }
    }
  };
</script>

<style scoped>

</style>
