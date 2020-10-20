import queryString from 'query-string'

const userMap = {
  logininfo: {
    departs: [{
      address: null,
      createBy: "admin",
      createTime: "2019-02-26 16:36:47",
      delFlag: "0",
      departName: "财务部",
      departNameAbbr: null,
      departNameEn: null,
      departOrder: 0,
      description: null,
      fax: null,
      id: "a7d7e77e06c84325a40932163adcdaa6",
      memo: null,
      mobile: null,
      orgCode: "A02A01",
      orgType: "2",
      parentId: "6d35e179cd814e3299bd588ea7daed3f",
      status: null,
      updateBy: "admin",
      updateTime: "2019-02-26 16:37:25",
    }],
    token: 'admin',
    multi_depart: 1,
    userInfo: {
      activitiSync: "1",
      avatar: "user/20190220/e1fe9925bc315c60addea1b98eb1cb1349547719_1550656892940.jpg",
      birthday: null,
      createBy: "admin",
      createTime: "2019-02-13 16:02:36",
      delFlag: "0",
      email: null,
      id: "a75d45a015c44384a04449ee80dc3503",
      orgCode: "A02A01",
      password: "3dd8371f3cf8240e",
      phone: null,
      realname: "sticker",
      salt: "vDDkDzrK",
      sex: 2,
      status: 1,
      updateBy: "admin",
      updateTime: "2019-04-09 15:47:36",
      username: "sticker",
    }
  }
}

export default {
  loginbyUser: config => {
    let { userId } = JSON.parse(config.body)
    let response = {
        code: 200,
        result: userMap.logininfo,
        message: "登录成功",
        success: true
      }
    return response
  },
  logout: () => {
    return {
      code: 200,
      data: {
        state: 1
      },
      message: 'Logout success'
    }
  },
  info: config => {
    let params = queryString.parse(config.url.replace(/^\S+(?=\?)/, ''))
    let response = {
      code: 200,
      data: userMap[params.userId],
      message: 'Login success, Welcome'
    }
    return response
  }
}
