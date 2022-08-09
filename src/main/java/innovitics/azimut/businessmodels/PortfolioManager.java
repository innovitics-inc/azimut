package innovitics.azimut.businessmodels;

public class PortfolioManager {

		private String id;
		private String image;
		private String name;
		private String title;
		private String country;
		private String joinDate;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getJoinDate() {
			return joinDate;
		}
		public void setJoinDate(String joinDate) {
			this.joinDate = joinDate;
		}
		@Override
		public String toString() {
			return "PortfolioManager [id=" + id + ", image=" + image + ", name=" + name + ", title=" + title
					+ ", country=" + country + ", joinDate=" + joinDate + "]";
		}
		
		
		
}
