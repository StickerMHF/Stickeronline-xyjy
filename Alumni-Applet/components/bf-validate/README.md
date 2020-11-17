# 通过 async-validator进行验证

- 加入了中文的报错提示
- 先 npm install async-validator
- 验证出现错误处理报错内容为 name + 错误
- [其他验证规则] (https://www.cnblogs.com/chenzxl/p/10879771.html)

```js
    <bf-validate :model="handleData" :rules="rules" ref="validator" />
    handleData: {
				images: [],
        remark: ''
    },
    rules: {
        images: { type: 'array', required: true, name: '照片' },
        remark: { required: true, name: '备注' }
    }
    // 报错内容为 照片 为必填
    handleSubmit() {
        this.$refs.validator.validate()
            .then(() => {
                // 验证通过
                // 其他的业务逻辑
            })
            .catch(err => {
                // 如果有错误会toast出来错误 也会这样抛出来
            })
    }
```