<template>
    <a-modal
            :visible="addCouponModalVisible"
            title="添加优惠策略"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠劵类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                        'coupontype',
                        {rules:[{required:true,message: '请选择优惠券类型'}]}
                    ]">
                    <a-select-option value=1>生日优惠</a-select-option>
                    <a-select-option value=2>多间优惠</a-select-option>
                    <a-select-option value=3>满减优惠</a-select-option>
                    <a-select-option value=4>限时优惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input placeholder="请填写券名" v-decorator="['name',{rules: [{required: true,message: '请填写券名'}]}]"/>
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :row="4"
                        placeholder="请填写优惠简介"
                        v-decorator="['description',{rules:[{required:true, message:'请填写优惠简介'}]}]"/>
            </a-form-item>
            <a-form-item label="有效时间" v-bind="formItemLayout">
                <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请选择优惠时间' }]
                        }
                    ]"
                        :placeholder="['开始日期','失效日期']"
                />
            </a-form-item>
            <a-form-item label="达标金额" v-bind="formItemLayout">
                <a-input placeholder="请填写达标金额" v-decorator="['targetnum',{rules: [{required: true,message: '请填写达标金额'}]}]"/>
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout">
                <a-input placeholder="请填写优惠金额" v-decorator="['decreasenum',{rules: [{required: true,message: '请填写优惠金额'}]}]"/>
            </a-form-item>
            <a-form-item label="折扣" v-bind="formItemLayout">
                <a-input-number style="width: 100%" placeholder="请填写折扣" v-decorator="['discount',{rules: [{required: true,message: '请填写折扣'}]}]"
                                :min="0" :max="9.5" :default-value="-1" :step="0.5"
                />
            </a-form-item>
        </a-form>
        <!-- 这里是添加策略模态框区域，请编写表单 -->
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    const moment = require('moment')
    export default {
        name: "addCouponModal",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: { span: 12 },
                        sm: { span: 6 },
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 },
                    },
                },
            }
        },
        computed: {
            ...mapGetters([
                'addCouponModalParams',
                'addCouponModalVisible',

            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'addCouponModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCouponModalVisible',
                'set_addCouponModalParams',
            ]),
            ...mapActions([
                'addWebCoupon',

            ]),
            cancel() {
                this.set_addCouponModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            // 这里添加接口参数
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: this.form.getFieldValue('coupontype'),
                            startDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                            endDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                            targetMoney: this.form.getFieldValue('targetnum'),
                            discountMoney: this.form.getFieldValue('decreasenum'),
                            discount:this.form.getFieldValue('discount'),
                        }
                        this.set_addCouponModalParams(data)
                        console.log(data)
                        this.addWebCoupon(data)
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>