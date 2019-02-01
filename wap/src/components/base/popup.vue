<template>
  <div>
    <!--提示框v-text-->
    <el-dialog title="提示"
               :visible.sync="openInfo.huidiao"
               width="300px">
      <span v-text="openInfo.huidiaoInfo"></span>
      <span slot="footer"
            class="dialog-footer">
        <el-button type="primary"
                   class="btncolor"
                   @click="openInfo.huidiao = false">确 定</el-button>
      </span>
    </el-dialog>

    <!--提示框v-html-->
    <!-- <el-dialog title="提示"
               :visible.sync="openInfo.huidiao4"
               size="small"
               custom-class="dialog300">
      <span v-html="openInfo.huidiaoInfo4"></span>
      <span slot="footer"
            class="dialog-footer">
        <el-button type="primary"
                   @click="openInfo.huidiao4 = false">确 定</el-button>
      </span>
    </el-dialog> -->

    <!--仅确认框，不能点击空白处关闭-->
    <!-- <el-dialog title="提示"
               :visible.sync="openInfo.huidiao2"
               size="small"
               :show-close="false"
               :before-close="handleClose2"
               custom-class="dialog300">
      <span v-text="openInfo.huidiaoInfo2"></span>
      <span slot="footer"
            class="dialog-footer">
        <el-button type="primary"
                   @click="openInfoComfirm2">确 定</el-button>
      </span>
    </el-dialog> -->

    <!--选择取消或确认框，不能点击空白处关闭-->
    <!-- <el-dialog title="提示"
               :visible.sync="openInfo.huidiao3"
               size="small"
               :show-close="false"
               :before-close="handleClose3"
               custom-class="dialog300">
      <span v-text="openInfo.huidiaoInfo3"></span>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="openInfo.huidiao3 = false">取 消</el-button>
        <el-button type="primary"
                   @click="openInfoComfirm3">确 定</el-button>
      </span>
    </el-dialog> -->

    <!--给403，404，20039用的-->
    <!-- <el-dialog title="提示"
               :visible.sync="openInfo.huidiaote"
               size="small"
               :show-close="false"
               :before-close="handleClose2"
               custom-class="dialog300 twoindexdialog twoindexInfo">
      <span v-text="openInfo.huidiaoInfote"></span>
      <span slot="footer"
            class="dialog-footer">
        <el-button type="primary"
                   @click="openInfoComfirmte">确 定</el-button>
      </span>
    </el-dialog> -->
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  data () {
    return {

    }
  },
  computed: mapGetters([
    'openInfo'
  ]),
  methods: {
    // handleClose2 (done) {
    //   this.openInfo.huidiao2 = true
    // },
    // handleClose3 (done) {
    //   this.openInfo.huidiao3 = true
    // },
    // openInfoComfirm2 () {
    //   this.$router.push({ path: this.openInfo.huidiaourl2 })
    //   this.openInfo.huidiao2 = false
    // },
    // openInfoComfirmte () {
    //   window.location.reload()
    //   this.$router.push({ path: this.openInfo.huidiaourlte })
    //   this.openInfo.huidiaote = false
    //   // location.reload();
    // },
    // openInfoComfirm3 () {
    //   this.$router.push({ path: this.openInfo.huidiaourl3 })
    //   this.openInfo.huidiao3 = false
    // }
  }
}
</script>

<style>
</style>
