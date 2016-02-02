package com.example.qiang_pc.newtalkpal.bean;

public class LoginBean {
	/**
	 * code : 0
	 * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
	 * .eyJuaWNrbmFtZSI6IuWQluWQliIsInVuaW9uaWQiOiJvV1ZGMnQ5RGpxeHlmeVYzc0JtMU54SXNxRUJBIiwiaWF0IjoxNDUzNDQ3MDU0fQ.zeRQswp0rGyP8ej0UzxqgUQ3gjabRriD4JkWmEXwM10","user":{"_id":"566e624b65e4604639059ccb","phone":"15018879654","nickname":"吖吖"},"bindPhone":true}
	 */

	private int code;
	private String error;


	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	/**
	 * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
	 * .eyJuaWNrbmFtZSI6IuWQluWQliIsInVuaW9uaWQiOiJvV1ZGMnQ5RGpxeHlmeVYzc0JtMU54SXNxRUJBIiwiaWF0IjoxNDUzNDQ3MDU0fQ.zeRQswp0rGyP8ej0UzxqgUQ3gjabRriD4JkWmEXwM10
	 * user : {"_id":"566e624b65e4604639059ccb","phone":"15018879654","nickname":"吖吖"}
	 * bindPhone : true
	 */

	private DataEntity data;

	public void setCode(int code) {
		this.code = code;
	}

	public void setData(DataEntity data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public DataEntity getData() {
		return data;
	}

	public static class DataEntity {
		private String token;
		/**
		 * _id : 566e624b65e4604639059ccb
		 * phone : 15018879654
		 * nickname : 吖吖
		 */

		private UserEntity user;
		private boolean bindPhone;

		public void setToken(String token) {
			this.token = token;
		}

		public void setUser(UserEntity user) {
			this.user = user;
		}

		public void setBindPhone(boolean bindPhone) {
			this.bindPhone = bindPhone;
		}

		public String getToken() {
			return token;
		}

		public UserEntity getUser() {
			return user;
		}

		public boolean isBindPhone() {
			return bindPhone;
		}

		public static class UserEntity {
			private String _id;
			private String phone;
			private String nickname;
			private String headimgurl;

			public String getHeadimgurl() {
				return headimgurl;
			}

			public void setHeadimgurl(String headimgurl) {
				this.headimgurl = headimgurl;
			}

			public void set_id(String _id) {
				this._id = _id;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}

			public String get_id() {
				return _id;
			}

			public String getPhone() {
				return phone;
			}

			public String getNickname() {
				return nickname;
			}
		}
	}
//	public int code;
//	public Data data=new Data();
//	public String error;
//
//	public class Data{
//		public String token;
//		public User user=new User();
//	}
//	public class User{
//		public String phone;
//		public String nickname;
//		public String _id;
//		public String headimgurl;
//		public boolean bindPhone;
//	}

}
