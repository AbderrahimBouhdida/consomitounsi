package tn.esprit.consomitounsi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ads  implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
		private int idAds;
		private String name;
		private Date StartDay;
		private Date EndDay;
		private int ViewsNumber;
		private int FinalViewsNumber;

		@ManyToOne
		private User user;
		
		public Ads() {
			super();
		}

		public Ads(int idAds, String name, Date startDay, Date endDay, int viewsNumber, int finalViewsNumber) {
			super();
			this.idAds = idAds;
			this.name = name;
			StartDay = startDay;
			EndDay = endDay;
			ViewsNumber = viewsNumber;
			FinalViewsNumber = finalViewsNumber;
		}

		public int getIdAds() {
			return idAds;
		}

		public void setIdAds(int idAds) {
			this.idAds = idAds;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getStartDay() {
			return StartDay;
		}

		public void setStartDay(Date startDay) {
			StartDay = startDay;
		}

		public Date getEndDay() {
			return EndDay;
		}

		public void setEndDay(Date endDay) {
			EndDay = endDay;
		}

		public int getViewsNumber() {
			return ViewsNumber;
		}

		public void setViewsNumber(int viewsNumber) {
			ViewsNumber = viewsNumber;
		}

		public int getFinalViewsNumber() {
			return FinalViewsNumber;
		}

		public void setFinalViewsNumber(int finalViewsNumber) {
			FinalViewsNumber = finalViewsNumber;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		
		
}