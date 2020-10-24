//post
export function postAction(url,parameter) {
  return uni.request({
    url: url,
    method:'post' ,
    data: parameter
  })
}

//post method= {post | put}
export function httpAction(url,parameter,method) {
  return uni.request({
    url: url,
    method:method ,
    data: parameter
  })
}

//put
export function putAction(url,parameter) {
  return uni.request({
    url: url,
    method:'put',
    data: parameter
  })
}

//get
export function getAction(url,parameter) {
  return uni.request({
    url: url,
    method: 'GET',
    data: parameter
  })
}

//deleteAction
export function deleteAction(url,parameter) {
  return uni.request({
    url: url,
    method: 'delete',
    data: parameter
  })
}

/**
 * 上传文件
 * uni.uploadFile({
            url: 'https://www.example.com/upload', //仅为示例，非真实的接口地址
            filePath: tempFilePaths[0],
            name: 'file',
            formData: {
                'user': 'test'
            },
            success: (uploadFileRes) => {
                console.log(uploadFileRes.data);
            }
        });
 */
// export function uploadFile(url,parameter){
// 	return uni.request({
// 		url:url,
		
// 	})
// }


/**
 * 下载文件 用于excel导出
 * @param url
 * @param parameter
 * @returns {*}
 */
export function downFile(url,parameter){
  return uni.request({
    url: url,
    params: parameter,
    method:'get' ,
    responseType: 'blob'
  })
}

