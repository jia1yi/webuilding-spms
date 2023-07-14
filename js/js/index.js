ElementPlus.locale(ElementPlus.lang.zhCn)
let qs = Qs || window.Qs;
const {
    reactive,
    ref,
    computed,
    toRefs,
    onBeforeMount,
    onMounted,
} = Vue;

const App = ({
    setup() {
        const domForm = ref()
        const appData = reactive({
            timerPicker: [new Date(),new Date()],
            pageNum: 1,
            pagesize: 10,
            queryFrom: {
                pageindex: 1,
                
                pagesize: 10,

                starttime: '',
                endtime: '',
              
                pername: '',
                doorId: ''
                
            },
            querySelect: [],
            queryType: [],
            equiList: {
                listInfo: [],
                tableData: [],
                loading: false,
            },
            msg: '',
            key: '',
            key1: '',
            data:'',
            // 分页
            pagination: {
                currentPag: 1,
                total: 0,
                pagesize: 10
            },
        })
        // 表头样式设置
         const headClass = () => {
            return `text-align:center;background:transparent;font-weight:bold;font-size:14px;color:#333;`;
        };
        // 表单搜索 重置
        const getList = () => {
            console.log("时间"+appData.timerPicker)
            // if (appData.timerPicker === null || appData.timerPicker.length === 0) {
            //     appData.queryFrom.starttime = parseTime(Date.now, '{y}-{m}-{d} 00:00:00')
            //     appData.queryFrom.endtime =  parseTime(Date.now, '{y}-{m}-{d} 23:59:59')
            // } else {
            //     appData.queryFrom.starttime =parseTime(appData.timerPicker[0], '{y}-{m}-{d} 00:00:00')
            //     appData.queryFrom.endtime = parseTime(appData.timerPicker[1], '{y}-{m}-{d} 23:59:59')
            // }
            appData.queryFrom.starttime =parseTime(appData.timerPicker[0], '{y}-{m}-{d} 00:00:00')
            appData.queryFrom.endtime = parseTime(appData.timerPicker[1], '{y}-{m}-{d} 23:59:59')
            getEquitList()
        };
        const resetForm = () => {
			domForm.value.resetFields();
            appData.timerPicker = []
			appData.queryFrom.starttime = ''
			appData.queryFrom.endtime = ''
            getEquitList()
        };
        const getRes = () => {
			getEquitList()
        };
        const handleSizeChange = (val) => {
            appData.pagination.currentPag = 1
            appData.pagination.pagesize = val
            appData.queryFrom.pagesize = val
            getEquitList()
        }
        const handleCurrentChange = (val) => {
            appData.pagination.currentPag = val
            appData.queryFrom.pageindex = val
            getEquitList()
        }
        const timeInit = (time) => {
            return dateFunction(time)
        }
        // 选择地点
        const getPlaceName = () => {
            axios.post(query2,{})  
                .then(res => {
                  appData.querySelect = res.data
                })
                .catch(err => {
                    console.log(err)
                })     
        }
       // getPlaceName()
        // 巡检器编号         
         const getDeviceNumber = () => {
            axios.post(query3,{})  
                .then(res => {
                  appData.queryType = res.data
                })
                .catch(err => {
                    console.log(err)
                })     
        }
        //getDeviceNumber()
        // 分页设置
        const getNeeder = (arrary, size) => {
            const length = arrary.length
            if (!length || !size || size<1) {
                return []
            }
            let index = 0 // 用来表示切割元素范围start
            let resIndex = 0 // 用来递增表述输出数据的下表
            
            let result = new Array(Math.ceil(length/size))
            while (index < length) {
                //循环过程中设置result[0]和result[1]的值。该值根据array.slice切割得到。
                result[resIndex++] = arrary.slice(index, (index+=size))
            }
            return result
        }
        // 设备列表
         const getEquitList = () =>  {
         appData.equiList.loading = true
         appData.queryFrom.starttime =parseTime(appData.timerPicker[0], '{y}-{m}-{d} 00:00:00')
         appData.queryFrom.endtime = parseTime(appData.timerPicker[1], '{y}-{m}-{d} 23:59:59')
       
             let url = 'https://q2153208e1.oicp.vip/spms/visitor/test1?test=123';
           ;
           let  pr='MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANr3cR1jDuAMVXjT41GC4RKDNF2X3rM2YG4QcXtMGxNbmVmliFbNrgzpclkgSQdjzxDef2Qkv2xZ+Xjv4X3sEjNoHRTm7+WsPF5AUK1fSnFZ/Va/A7SFDAJUr4ErVnidMFREA11KUwbcvlIW3rg/h84hKSz2ocQcdFUoXEQNuSGnAgMBAAECgYEAzWX3cX2OYpSvSJxaTAfdYSQ6ZuEjQjDr29MI4IPPkiUGgJVKg0KE1g/V98zVNF1ny4crMKFX/yeXYN6EQz7DAkCUAYirXfNZDNkbyXMul/QYEPmmAhx4n/WFNftQZ+wyPLAFQvZe05jMK5vL+qYASBAlEVtjdxkSwrVym7TVriECQQD9t0lwofxbcqdXBVhQKDRDXtNF7B8u3KZIdXN8L8EHQM3FajDbLUde6JZCxxBW8f2oGJWmnJuNpunKTTEH9lMLAkEA3PASVZ6nNay2ySTPkzCEZuwMCS24rBtJRDjwhAiBMsjk8koB7WH4oFUS9f2tdrTWausbVVSNMlUKdOgb5ygNVQJBAJooH4s8ISU/SMXrDXnGs9qrZxt5GIlUsAQeuQify10o1t44Y28I1/CmyYXRhfAOIN1d8q/FQA6VeD/fHweDd3kCQGiup0FCJt/bsc+kTWXkOxe5CArhSvrhAtn8UJl+nhi97xyNzFP5c6AkLxO22pXAIUgPlafco/oRKR8zSuU/qPECQGVxLY2fXLBmrD4SJwxx0AuQi3s89bEYlvhoECJKFYtzGVmdshjgqVtcsxXIWatBsDr+ktx5Td9HgKkX6qxos7U=';
        console.log("请求参数"+url)
            axios.get(url)  
                .then(res => {
                    appData.msg=res.data.msg
                	 console.log('消息密文:'+res.data.msg)
                	 	let decrypt = new JSEncrypt()
			　　decrypt.setPrivateKey(pr)
			　　		 //密钥解密
			　　var encrypted = decrypt.decryptLong(res.data.encrypted)
                    appData.key= res.data.encrypted
			　　console.log('密钥密文：'+ res.data.encrypted)
                	 	  console.log('密钥明文：'+ encrypted)
                    appData.key1=encrypted
                    var data=decrypt1(res.data.msg,encrypted);
               		 console.log('消息明文：'+data)
                    appData.data=data
                })
                .catch(err => {
                    appData.equiList.loading = false
                    console.log(err)
                })  
           
        }
        getEquitList()
        
		// 导出
        const base64 = s => window.btoa(unescape(encodeURIComponent(s)));
        const toExecl = () => {
            appData.equiList.loading = false
         appData.queryFrom.starttime =parseTime(appData.timerPicker[0], '{y}-{m}-{d} 00:00:00')
         appData.queryFrom.endtime = parseTime(appData.timerPicker[1], '{y}-{m}-{d} 23:59:59')
       
             let url = mjjl + '?pageNo=' + appData.queryFrom.pageindex +'&pageSize='+2000
            + '&empNo=' + appData.queryFrom.pername + '&doorId=' + appData.queryFrom.doorId+'&starttime='+appData.queryFrom.starttime+ '&endtime='+appData.queryFrom.endtime;
        console.log("请求参数"+url)
            console.log(appData.queryFrom)
            axios.get(url) 
			.then(res => {
				let  infos =null;
				if(res.data.data!=null){
						console.log(res.data.data)
						infos = res.data.data.datalist;
						 let str = `<tr>
                    <td>卡号</td>
                    <td>姓名</td>
                    <td>时间</td>
                    <td>门的名称</td>
                    <td>记录类型</td>
                    <td>部门名称</td>
                    <td>进出标记</td>
                </tr>`
                for(let i = 0 ; i < infos.length  ; i++ ){
					
					let flag=infos[i].InOutFlag== "1" ? "进" : infos[i].InOutFlag == "2" ? "出":"/";
                    str+='<tr>';
                    str+=`<td>${infos[i].empCardId + '\t'}</td>`;     
                    str+=`<td>${infos[i].empName + '\t'}</td>`;     
                    str+=`<td>${infos[i].cardDay  +'\t'}</td>`;    
                    str+=`<td>${infos[i].doorName + '\t'}</td>`;     
                    str+=`<td>${infos[i].RecordType + '\t'}</td>`;     
                    str+=`<td>${infos[i].dptName + '\t'}</td>`;         
                    str+=`<td>${flag + '\t'}</td>`;     
                    str+='</tr>';
                }
				const worksheet = '123123'
				const uri = 'data:application/vnd.ms-excel;base64,';
		
				// 下载的表格模板数据
				const template = `<html 
					xmlns:o="urn:schemas-microsoft-com:office:office" 
					xmlns:x="urn:schemas-microsoft-com:office:excel" 
					xmlns="http://www.w3.org/TR/REC-html40">
					<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>
					<x:Name>${worksheet}</x:Name>
					<x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>
					</x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
					</head><body><table>${str}</table></body>
				</html>`;
				// 下载模板
				window.location.href = uri + base64(template);
					}else{
						appData.equiList.tableData = [];
						alert("没有找到要导出的数据！！！");
					}
					
				  
               
			})
			.catch(err => console.log(err))
        }
        return {
            domForm,
            ...toRefs(appData),
            timeInit,
            getList,
            resetForm,
            headClass,
            handleSizeChange,
            handleCurrentChange,
            getRes,
			toExecl
        }
    }
})

Vue
    .createApp(App)
    .use(ElementPlus, { size: "small"})
    .mount("#app")
