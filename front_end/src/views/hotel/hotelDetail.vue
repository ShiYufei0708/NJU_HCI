<template>
  <a-layout>
    <a-layout-content>
      <div class="hotelDetailCard">
        <div class="hotel-info" >
          <div class="text-pic-container" style="width: 70%;">
            <div class="headerLine">
              <h1 style="width: 50%">
                {{ currentHotelInfo.name }}        <a-rate style="font-size: 10px;" :value="currentHotelInfo.hotelStar" disabled allowHalf />
              </h1>
              <p class="font-italic" style="width: 50%">
                <a-icon type="environment" />  地点：  {{ currentHotelInfo.address }}
              </p>
              <p class="font-italic" style="width: 50%">
                <a-icon type="tag" />  商圈： {{ currentHotelInfo.bizRegion }}
              </p>
            </div>
            <div class="picture-container">
              <a-card class="hotel_img" >
                <img
                    alt="example"
                    :src="image_url"
                    slot="cover"
                    referrerPolicy="no-referrer"
                />
              </a-card>
            </div>
          </div>

          <div class="right-part-container" style="display: flex;flex-direction: column;margin: 30px;margin-top:30px;margin-right: 0">
            <div class="comment-container" v-if="modify===true" style=" padding: 5px;padding-top: 20px;padding-bottom: 0">
              <v-container>
                <v-row>
                  <v-card
                      class="mx-auto"
                      color="#7dc5eb"
                      dark
                      max-width="500px"
                      max-height="190px"
                  >
                    <v-card-title>
                      <a-icon type="smile" theme="twoTone" />
                      <span class="text-h6 font-weight-light">精选评论</span>
                    </v-card-title>

                    <v-card-text class="text-h5 font-weight-bold">
                      "Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well."
                    </v-card-text>

                    <v-card-actions>
                      <v-list-item class="grow">
                        <v-list-item-avatar color="grey darken-3">
                          <v-img
                              class="elevation-6"
                              alt=""
                              src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                          ></v-img>
                        </v-list-item-avatar>

                        <v-list-item-content>
                          <v-list-item-title>Frank</v-list-item-title>
                        </v-list-item-content>

                        <v-row
                            align="center"
                            justify="end"
                        >
                          <a-icon type="heart" theme="twoTone" two-tone-color="#eb2f96" />
                          <span class="subheading">45</span>
                        </v-row>
                      </v-list-item>
                    </v-card-actions>
                  </v-card>
                </v-row>
              </v-container>

            </div>
            <div class="map-container" v-if="modify===true">
              <div class="amap-wrapper" style="padding-top: 40px">
                <el-amap class="amap-box" :vid="'amap-vue'"></el-amap>
              </div>
            </div>
            <div class="info"  v-if="modify">
              <a-form :form="form" style="margin-top: 10px;">
                <a-form-item label="酒店名称" :label-col="{ span: 10 }" :wrapper-col="{ span: 10, offset: 3  }">
                  <a-input
                      placeholder="请填写酒店名称"
                      v-decorator="['name', { rules: [{ required: true, message: '请输入酒店名称' }] }]"
                      v-if="modify"
                  />
                  <span v-else>{{ currentHotelInfo.name }}</span>
                </a-form-item>
                <a-form-item label="地址" :label-col="{ span: 10 }" :wrapper-col="{ span: 10, offset: 3 }">
                  <a-input
                      placeholder="请填写酒店地址"
                      v-decorator="['address', { rules: [{ required: true, message: '请输入酒店地址' }] }]"
                      v-if="modify"
                  />
                  <span v-else>{{ currentHotelInfo.address }}</span>
                </a-form-item>
                <a-form-item label="商圈" :label-col="{ span: 10 }" :wrapper-col="{ span: 10, offset: 3 }">
                  <a-input
                      placeholder="请填写酒店所在商圈"
                      v-decorator="['bizRegion', { rules: [{ required: true, message: '请输入酒店所在商圈' }] }]"
                      v-if="modify"
                  />
                  <span v-else>{{ currentHotelInfo.bizRegion }}</span>
                </a-form-item>
                <a-form-item label="评分" :label-col="{ span: 10 }" :wrapper-col="{ span: 10, offset: 3 }">
                  <span class="value">{{ currentHotelInfo.rate }}</span>
                </a-form-item>

                <a-form-item label="星级" :label-col="{ span: 10 }" :wrapper-col="{ span: 10, offset: 3 }">
                  <a-rate style="font-size: 15px" v-if="modify" v-model="values"/>
                  <a-rate style="font-size: 15px" :value="currentHotelInfo.hotelStar" v-else disabled/>
                </a-form-item>

                <a-form-item label="酒店简介" :label-col="{ span: 10 }" :wrapper-col="{ span: 10, offset: 3 }">
                  <a-input
                      placeholder="请填写酒店简介"
                      v-decorator="['description', { rules: [{ required: true, message: '请输入酒店简介' }] }]"
                      v-if="modify"
                  />
                  <span v-else >{{ currentHotelInfo.description }}</span>
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                  <a-button type="primary" @click="saveModify">
                    保存
                  </a-button>
                  <a-button type="default" style="margin-left: 10px" @click="cancelModify">
                    取消
                  </a-button>
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else-if="userInfo.userType=='HotelManager'">
                  <a-button type="primary" @click="modifyInfo">
                    修改信息
                  </a-button>
                </a-form-item>
              </a-form>
              <div style=" margin-left: 40px;">
                <a-upload
                    v-if="userInfo.userType=='HotelManager'"
                    action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                    list-type="text"
                    :customRequest="testUpload"
                    :fileList="temp"
                >
                  <a-button>
                    <a-icon type="upload"/>
                    重新上传图片
                  </a-button>
                </a-upload>
              </div>

            </div>
          </div>
        </div>


        <a-divider></a-divider>
        <a-tabs type="card">
          <a-tab-pane tab="房间信息" key="1">
            <div style="width: 100%; text-align: left; margin:20px 0">
              <a-range-picker v-if="userInfo.userType=='Client'" @change="onChange"/>
            </div>
            <a-alert
                v-if="userInfo.userType=='Client'&&flag==true"
                message="提示"
                description="如果您没有选择时间段，将默认返回近两个月内一直空闲的房间"
                type="info"
                show-icon
            />
            <br/>
            <a-table
                v-if="userInfo.userType=='Client'"
                :columns="columns"
                :dataSource="roomList"
                bordered
            >

                        <span slot="action" slot-scope="record">
                            <a-button type="primary" @click="order(record)">预定</a-button>
                        </span>
            </a-table>

            <RoomList :rooms="currentHotelInfo.rooms" v-if="userInfo.userType=='HotelManager'"></RoomList>
          </a-tab-pane>
          <a-tab-pane tab="入住详情" key="2" v-if="userInfo.userType=='Client'">
            <a-timeline class="hotel-detail">
              <template v-for="item in historyOrderList">
                <a-timeline-item :key="item.id"></a-timeline-item>
                <a-timeline-item :key="item.id" color="green">入住于{{ item.checkInDate }}</a-timeline-item>
                <a-timeline-item :key="item.id">
                  <a-icon slot="dot" type="clock-circle-o" style="font-size: 16px;"/>
                  房间类型：{{ item.roomType }}
                  房间数量：{{ item.roomNum }}
                  入住人数：{{ item.peopleNum }}
                  价格：{{ item.price }}
                </a-timeline-item>
                <a-timeline-item color="red" :key="item.id">
                  离开于{{ item.checkOutDate }}
                </a-timeline-item>

              </template>
            </a-timeline>
          </a-tab-pane>
          <a-tab-pane tab="历史评价" key="3">
            <HistoryComment :comments="currentHotelInfo.historyCommentVOs"></HistoryComment>
          </a-tab-pane>
        </a-tabs>
      </div>
      <order-modal></order-modal>
    </a-layout-content>
  </a-layout>
</template>
<script>
import OrderModal from "./components/orderModal";

let OSS = require('ali-oss');
let client = new OSS({
  accessKeyId: 'LTAI4GAh9znuBWPxpPX1q5z5',
  accessKeySecret: '2s6H9ljuP6oO3aqLosalsUfTx35siO',
  bucket: 'obsidian222',
  region: 'oss-cn-shenzhen',
  secure: true,
})
import HistoryComment from "./components/historyComment";

const timeList = [{
  checkInDate: "2020-07-02",
  checkOutDate: "2020-07-03",
  clientName: "测试二号",
  createDate: "2020-07-02",
  haveChild: false,
  hotelId: 1,
  hotelName: "汉庭酒店",
  id: 14,
  orderState: "已预订",
  peopleNum: 2,
  phoneNumber: "12345678911",
  price: 348,
  roomId: 2,
  roomNum: 2,
  roomType: "BigBed",
  userId: 5,
},
  {
    checkInDate: "2020-07-02",
    checkOutDate: "2020-07-03",
    clientName: "测试二号",
    createDate: "2020-07-02",
    haveChild: false,
    hotelId: 1,
    hotelName: "汉庭酒店",
    id: 14,
    orderState: "已预订",
    peopleNum: 2,
    phoneNumber: "12345678911",
    price: 348,
    roomId: 2,
    roomNum: 2,
    roomType: "BigBed",
    userId: 5,
  },
]
const columns = [
  {
    title: '房型',
    dataIndex: 'roomType',
    key: 'roomType',
  },
  {
    title: '早餐',
    dataIndex: 'breakfast',
    key: 'breakfast',
  },
  {
    title: '可用房间',
    dataIndex: 'total',
    key: 'total',
  },
  {
    title: '剩余房间',
    dataIndex: 'curNum',
    key: 'curNum',
  },
  {
    title: '入住人数',
    key: 'peopleNum',
    dataIndex: 'peopleNum',
  },
  {
    title: '房价',
    key: 'price',
    dataIndex: 'price',
    scopedSlots: {customRender: 'price'}
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: {customRender: 'action'},
  },
];

const cards=[
  { title: 'Pre-fab homes', src: 'https://cdn.vuetifyjs.com/images/cards/house.jpg', flex: 12 },
  { title: 'Favorite road trips', src: 'https://cdn.vuetifyjs.com/images/cards/road.jpg', flex: 6 },
  { title: 'Best airlines', src: 'https://cdn.vuetifyjs.com/images/cards/plane.jpg', flex: 6 },
];

import {AMapManager, lazyAMapApiLoaderInstance} from 'vue-amap'
let amapManager = new AMapManager()

import {mapGetters, mapActions, mapMutations} from 'vuex'
import RoomList from './components/roomList'

export default {
  name: 'hotelDetail',
  components: {
    OrderModal,
    HistoryComment,
    RoomList
  },
  data() {
    return {
      flag: true,
      cards,
      columns,
      timeList,
      temp: [],
      modify: false,
      values: '',
      form: this.$form.createForm(this, {name: 'coordinated'}),
      key: +new Date(),
    }
  },
  props: {  },
  computed: {
    ...mapGetters([
      'userId',
      'currentHotelInfo',
      'userInfo',
      'currentHotelId',
      'currentHotelUrl',
      'roomList',
      'historyOrderList',
    ]),
    image_url: function () {
      // return "https://test-nju-1.oss-cn-shenzhen.aliyuncs.com/hotel/timg.png"
      return this.currentHotelUrl
    },


  },
  async mounted() {
    this.set_currentHotelId(Number(this.$route.params.hotelId))
    console.log(this.currentHotelInfo.id)
    await this.getHotelById()
    await this.getHotelUrlById()
    let data = []
    data.push(this.userId, this.currentHotelInfo.id)
    this.getHistoryOrders(data)
    this.getHotelRoom(this.currentHotelId)
    this.values = this.currentHotelInfo.hotelStar
    // console.log(this.currentHotelInfo.historyComments)
    console.log(this.currentHotelUrl)
    this.testMethods()
  },
  beforeRouteUpdate(to, from, next) {
    this.set_currentHotelId(Number(to.params.hotelId))
    this.getHotelById()
    next()
  },
  methods: {
    ...mapMutations([
      'set_currentHotelId',
      'set_currentOrderRoom',
      'set_orderModalVisible'
    ]),
    ...mapActions([
      'getHotelById',
      'updateHotelInfo',
      'getHotelUrlById',
      'updateUrl',
      'searchRoomlByDate',
      'getHistoryOrders',
      'getHotelRoom',

    ]),

    order(record) {
      this.set_currentOrderRoom(record)
      this.set_orderModalVisible(true)
    },
    onChange(date, dateString) {
      console.log(date, dateString);
      this.searchRoomlByDate({
        start: dateString[0],
        end: dateString[1]
      })
      if (dateString[0] == "" && dateString[1] == "") {
        this.flag = true
      } else this.flag = false
    },
    saveModify() {
      this.form.validateFields((err, values) => {
        if (!err) {
          const data = {
            name: this.form.getFieldValue('name'),
            address: this.form.getFieldValue('address'),
            bizRegion: this.form.getFieldValue('bizRegion'),
            hotelStar: this.values,
            description: this.form.getFieldValue('description'),
            id: this.currentHotelInfo.id
          }
          this.updateHotelInfo(data).then(() => {
            this.modify = false
          })
        }
      });
    },


    cancelModify() {
      this.modify = false
    },
    modifyInfo() {
      console.log(this.currentHotelInfo)
      setTimeout(() => {
        this.form.setFieldsValue({
          'name': this.currentHotelInfo.name,
          'address': this.currentHotelInfo.address,
          'bizRegion': this.currentHotelInfo.bizRegion,
          'hotelStar': this.values,
          'description': this.currentHotelInfo.description,
        })
      }, 0)
      this.modify = true
    },
    testMethods() {

    },
    updateUrls(data) {
      this.updateUrl(data)
    },
    async testUpload(file) {
      try {
        //object-name可以自定义为文件名（例如file.txt）或目录（例如abc/test/file.txt）的形式，实现将文件上传至当前Bucket或Bucket下的指定目录。
        let result = await client.put('hotel/' + file.file.name, file.file);
        const tempData = {
          url: result.url
        }
        await this.updateUrls(tempData)

      } catch (e) {
        console.log(e);
      }
      return new Promise(resolve => {

      });
    },

  }
}
</script>
<style scoped lang="less">
.hotelDetailCard {
  padding: 50px 50px;
}
.hotel_img{
  width: 100%;
  margin:auto;
}
.amap-wrapper {
  width: 500px;
  height: 350px;
}

.hotel-info {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;

  .info {
    padding: 10px 0;
    display: flex;
    width: 280px;
    flex-direction: column;
    margin-left: 20px;

    .label {
      margin-right: 15px;
      font-size: 30px;
    }

    .value {
      margin-right: 15px;
    }
  }

}

.hotel-detail {
  margin-left: 50px;
  font-size: 40px;
  font-family: Avenir, 'WenQuanYi Micro Hei', Arial, Helvetica, sans-serif;
}
</style>
