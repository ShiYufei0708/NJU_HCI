<template>
  <div class="info-wrapper">
    <a-tabs :activeKey="activeKey" @change="changeKey" tabPosition="left">
      <a-tab-pane tab="我的信息" key="1">
        <a-form :form="form" style="margin-top: 30px">
          <!--          <a-form-item label="头像" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">-->
          <!--            <a-avatar-->
          <!--                shape="square"-->
          <!--                size="large"-->
          <!--                :style="{ backgroundColor: color, verticalAlign: 'middle' }"-->
          <!--            >-->
          <!--              {{ avatarValue }}-->
          <!--            </a-avatar>-->
          <!--            <a-divider type="vertical"></a-divider>-->
          <!--            <a-button-->
          <!--                size="small"-->
          <!--                :style="{ marginLeft: 16, verticalAlign: 'middle' }"-->
          <!--                @click="changeValue"-->
          <!--                v-if="modify"-->
          <!--            >-->
          <!--              改变-->
          <!--            </a-button>-->
          <!--          </a-form-item>-->
          <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
            <a-input
                placeholder="请填写用户名"
                v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                v-if="modify"
            />
            <span v-else>{{ userInfo.userName }}</span>
          </a-form-item>
          <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
            <span>{{ userInfo.email }}</span>
          </a-form-item>

          <a-form-item label="出生日期" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
            <a-date-picker
                v-if="modify"
                format="YYYY-MM-DD"
                v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请输入出生日期' }]
                        }
                    ]"
                :placeholder="'出生日期'"
            />
            <a-date-picker v-else :value="moment(userInfo.birthday, 'YYYY-MM-DD')" disabled></a-date-picker>
          </a-form-item>

          <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
            <a-input
                placeholder="请填写手机号"
                v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                v-if="modify"
            />
            <span v-else>{{ userInfo.phoneNumber }}</span>
          </a-form-item>
          <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
            <span>{{ userInfo.credit }}</span>
          </a-form-item>
          <!--          <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">-->
          <!--            <a-input-->
          <!--                placeholder="请输入新密码"-->
          <!--                v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"-->
          <!--            />-->
          <!--          </a-form-item>-->
          <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
            <a-button type="primary" @click="saveModify">
              保存
            </a-button>
            <a-button type="default" style="margin-left: 30px" @click="cancelModify">
              取消
            </a-button>
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
            <a-button type="primary" @click="modifyInfo">
              修改信息
            </a-button>
          </a-form-item>
        </a-form>
      </a-tab-pane>
      <a-tab-pane tab="我的订单" key="2" v-if="userInfo.userType=='Client'">
        <a-table
            :columns="columns"
            :dataSource="userOrderList"
            bordered
        >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
          <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
          <a-tag slot="orderState" color="blue" slot-scope="text">
            {{ text }}
          </a-tag>
          <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="checkInfo(record)">查看详情</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                            title="你确定撤销该笔订单吗？"
                            @confirm="confirmCancelOrder(record.id)"
                            @cancel="cancelCancelOrder"
                            okText="确定"
                            cancelText="取消"
                            v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState == '已入住'"></a-divider>
                       <a-popconfirm
                           title="你确定删除该笔订单的历史记录吗？"
                           @confirm="confirmCancelOrder(record.id)"
                           @cancel="cancelCancelOrder"
                           okText="确定"
                           cancelText="取消"
                           v-if="record.orderState === '已入住'"
                       >
                            <a-button type="danger" size="small" v-if="record.orderState === '已入住'">删除</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState === '已入住'"></a-divider>
                        <a-button type="primary" size="small" v-if="record.orderState==='已入住'"
                                  @click="addcomment(record)"><a-icon type="plus"/>评价</a-button>

                    </span>
        </a-table>
      </a-tab-pane>
      <a-tab-pane tab="修改密码" key="3" v-if="userInfo.userType=='Client'">
        <div class="components-input-demo-presuffix">
          <a-form-item label="当前密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" style="margin-top:24px">
            <a-input
                size="large"
                type="password"
                v-decorator="[
                'registerPassword',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>

          <a-form-item label="修改密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
            <a-input
                size="large"
                type="password"
                v-decorator="[
                'registerPassword',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item label="确认密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
            <a-input
                size="large"
                type="password"
                v-decorator="[
                'registerPasswordconfirm',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePasswordCheck }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 10, offset: 2 }" style="margin-top:24px">
            <a-button
                block
                type="primary"
                class="login-button"
                :loading="loginLoading"
                @click="handlelogin()"
            >确定</a-button>

          </a-form-item>
        </div>
      </a-tab-pane>
      <!--      <a-tab-pane tab="会员中心" key="4" v-if="userInfo.userType==='Client'">-->
      <!--        <div class="vip-info" v-if="userInfo.isVIP>0">-->
      <!--          <a-row>-->
      <!--            <a-col :span="1">-->
      <!--            </a-col>-->
      <!--            <a-col :span="9">-->
      <!--              <div class="info">-->
      <!--                <a-form :form="form" style="margin-top: 30px">-->
      <!--                  <a-form-item label="会员等级" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">-->
      <!--                    <a-rate style="font-size: 15px" :value="userInfo.isVIP" disabled/>-->
      <!--                    <a-tag color="#2db7f5">-->
      <!--                      level{{ userInfo.isVIP }}-->
      <!--                    </a-tag>-->
      <!--                  </a-form-item>-->

      <!--                  <a-form-item v-if="userInfo.corporationName.length>0" label="合作企业" :label-col="{ span: 3 }"-->
      <!--                               :wrapper-col="{ span: 8, offset: 1 }">-->
      <!--                    <a-tag color="orange">-->
      <!--                      {{ userInfo.corporationName }}-->
      <!--                    </a-tag>-->
      <!--                  </a-form-item>-->
      <!--                  <a-form-item v-else :wrapper-col="{ span: 8, offset: 1  }">-->
      <!--                    <a-button type="primary" @click="improveVip">-->
      <!--                      <a-icon type="rise"/>-->
      <!--                      升级为企业会员-->
      <!--                    </a-button>-->
      <!--                  </a-form-item>-->

      <!--                </a-form>-->
      <!--              </div>-->
      <!--            </a-col>-->
      <!--            <a-col :span="12">-->
      <!--              <a-card style="width: 700px;">-->
      <!--                <img-->
      <!--                    v-if="userInfo.corporationName.length>0"-->
      <!--                    alt="example"-->
      <!--                    src="https://obsidian-test.oss-cn-beijing.aliyuncs.com/QQ20200703-0.png"-->
      <!--                    slot="cover"-->
      <!--                    referrerPolicy="no-referrer"-->
      <!--                />-->
      <!--                <img-->
      <!--                    v-else-->
      <!--                    alt="example"-->
      <!--                    src="https://obsidian-test.oss-cn-beijing.aliyuncs.com/QQ20200703-1.png"-->
      <!--                    slot="cover"-->
      <!--                    referrerPolicy="no-referrer"-->
      <!--                />-->
      <!--              </a-card>-->
      <!--            </a-col>-->
      <!--          </a-row>-->
      <!--        </div>-->

      <!--        <div class="register" v-else>-->
      <!--          <a-button type="primary" style="margin-left: 30px" @click="beVip">-->
      <!--            注册会员-->
      <!--          </a-button>-->
      <!--        </div>-->
      <!--      </a-tab-pane>-->
    </a-tabs>
    <CheckOrderModal></CheckOrderModal>
    <AddCommentModal></AddCommentModal>
  </div>
</template>
<script>
import {getUserInfoAPI} from "../../api/user";

const moment = require('moment')
const colorList = ['#f56a00', '#7265e6', '#ffbf00', '#00a2ae'];
import {mapGetters, mapMutations, mapActions} from 'vuex'
import AddCommentModal from './components/addComment'
import CheckOrderModal from './components/checkOrder'

const columns = [
  {
    title: '订单号',
    dataIndex: 'id',
  },
  {
    title: '酒店名',
    dataIndex: 'hotelName',
  },
  {
    title: '房型',
    dataIndex: 'roomType',
    scopedSlots: {customRender: 'roomType'}
  },
  {
    title: '入住时间',
    dataIndex: 'checkInDate',
    scopedSlots: {customRender: 'checkInDate'}
  },
  {
    title: '离店时间',
    dataIndex: 'checkOutDate',
    scopedSlots: {customRender: 'checkOutDate'}
  },
  {
    title: '入住人数',
    dataIndex: 'peopleNum',
  },
  {
    title: '房价',
    dataIndex: 'price',
  },
  {
    title: '状态',
    filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'}],
    onFilter: (value, record) => record.orderState.includes(value),
    dataIndex: 'orderState',
    scopedSlots: {customRender: 'orderState'}
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: {customRender: 'action'},
  },

];
export default {
  name: 'info',
  data() {
    return {
      activeKey: "1",
      avatarValue: "1",
      color: colorList[0],
      modify: false,
      formLayout: 'horizontal',
      pagination: {},
      columns,
      data: [],
      form: this.$form.createForm(this, {name: 'coordinated'}),
    }
  },
  components: {
    AddCommentModal,
    CheckOrderModal,

  },
  computed: {
    ...mapGetters([
      'userId',
      'userInfo',
      'userOrderList',
      'addCommentVisible',
      'activeOrderId',
    ]),
  },
  created() {
    let defaultTab = this.$route.query.toTab;
    if (defaultTab) this.activeKey = defaultTab;
  },
  async mounted() {
    console.log(this.userInfo)
    await this.getUserInfo()
    await this.getUserOrders()
    this.avatarValue = this.userInfo.userName
  },
  methods: {
    moment,
    changeKey(key) {
      this.activeKey = key;
    },
    changeValue() {
      const index = colorList.indexOf(this.color);
      this.color = index < colorList.length - 1 ? colorList[index + 1] : colorList[0];
    },
    ...mapMutations([
      'set_addCommentVisible',
      'set_activeOrderId',
      'set_commentParams',
      'set_enrollVIPVisible',
      'set_checkOrderParams',
      'set_checkOrderModalVisible'
    ]),
    ...mapActions([
      'getUserInfo',
      'getUserOrders',
      'updateUserInfo',
      'cancelOrder'
    ]),
    saveModify() {
      this.form.validateFields((err, values) => {
        if (!err) {
          const data = {
            userName: this.form.getFieldValue('userName'),
            phoneNumber: this.form.getFieldValue('phoneNumber'),
            password: this.form.getFieldValue('password'),
            avatarValue: this.form.getFieldValue('userName'),
            birthday: this.form.getFieldValue('date').format('YYYY-MM-DD'),
          }
          this.avatarValue = data.userName
          this.updateUserInfo(data).then(() => {
            this.modify = false
          })
        }
      });
    },
    modifyInfo() {
      setTimeout(() => {
        this.form.setFieldsValue({
          'userName': this.userInfo.userName,
          'phoneNumber': this.userInfo.phoneNumber,
          'birthday': this.userInfo.birthday,
        })
      }, 0)
      this.modify = true
    },
    cancelModify() {
      this.modify = false
    },
    confirmCancelOrder(orderId) {
      this.cancelOrder(orderId)
    },
    cancelCancelOrder() {

    },
    beVip() {
      this.set_enrollVIPVisible(true)
    },
    improveVip() {
      this.set_enrollVIPVisible(true)
    },
    addcomment(record) {
      this.set_activeOrderId(record.id);

      this.set_commentParams({
        userId: this.userId,
        userName: this.userInfo.userName,
        credit: this.userInfo.credit,
        orderId: record.id,
        hotelId: record.hotelId,
        color: colorList[Math.floor(Math.random() * colorList.length)]
      })
      this.set_addCommentVisible(true);
    },
    checkInfo(record) {
      this.set_activeOrderId(record.id);
      const data = {
        checkInDate: record.checkInDate,
        checkOutDate: record.checkOutDate,
        clientName: record.clientName,
        createDate: record.createDate,
        haveChild: record.haveChild,
        hotelId: record.hotelId,
        hotelName: record.hotelName,
        id: record.id,
        orderState: record.orderState,
        peopleNum: record.peopleNum,
        phoneNumber: record.phoneNumber,
        price: record.price,
        roomId: record.roomId,
        roomNum: record.roomNum,
        roomType: record.roomType,
        userId: record.userId,
      }
      this.set_checkOrderParams(data)
      this.set_checkOrderModalVisible(true);
    }
  }
}
</script>
<style scoped lang="less">
.info-wrapper {
  padding: 50px;

  .chart {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px
  }
}
</style>
<style lang="less">
.info-wrapper {
  .ant-tabs-bar {
    padding-left: 30px
  }
}
</style>
<style lang="less">

</style>
