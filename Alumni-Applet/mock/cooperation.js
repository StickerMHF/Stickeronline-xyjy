import queryString from 'query-string'

const cooperation = {
	CooperationList: [{
		title: 'ss',
		contents:"合作合作合作合作合作合作合作合作合作合作合作合作合作合作合作合作合作合作合作合作",
		lastModifyDate:"2020-10-15",
		useName:"1985级stickeronline"
	}]
}

export default {
	getCooperationList: config => {
		// let { userId } = JSON.parse(config.body)
		let params = queryString.parse(config.url.replace(/^\S+(?=\?)/, ''))
		let response = {
			code: 200,
			data: cooperation.CooperationList,
			message: 'Login success, Welcome'
		}
		return response
	}
}
