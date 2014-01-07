package qinyuan.hrmis.dao;

import qinyuan.lib.web.html.ChkBox;

public class Resume {

	private int id;
	private String addTime;
	private String mdfTime;
	private SimpleUser creator;
	private SimpleUser tracer;
	private String company;
	private String applicant;
	private Post post;
	private String tel;
	private String email;
	private String qq;
	private String resumeNo;
	private String resumeLink;
	private SimpleSource source;
	private String intention;
	private Double experience;
	private String expectSalary;
	private String jhReason;
	private String education;
	private String skill;
	private String prevJob;
	private String prevProj;
	private String other;
	private String content;
	private boolean intentionRed;
	private TargetPlace targetPlace;
	private boolean downloaded;
	private Gender gender;

	Resume() {
	}

	public String getMdfTime() {
		return mdfTime.replaceAll(".0$", "");
	}

	public void setMdfTime(String mdfTime) {
		this.mdfTime = mdfTime;
	}

	public boolean getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}

	public TargetPlace getTargetPlace() {
		return targetPlace;
	}

	public void setTargetPlace(TargetPlace targetPlace) {
		this.targetPlace = targetPlace;
	}

	public int getTargetPlaceId() {
		return targetPlace.getId();
	}

	public String getTargetPlaceName() {
		return targetPlace == null ? null : targetPlace.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddTime() {
		return addTime.replaceAll(".0$", "");
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public SimpleUser getCreator() {
		return creator;
	}

	public void setCreator(SimpleUser creator) {
		this.creator = creator;
	}

	public SimpleUser getTracer() {
		return tracer;
	}

	public void setTracer(SimpleUser tracer) {
		this.tracer = tracer;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = getStr(company, 200);
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = getStr(applicant, 10);
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = getStr(tel, 50);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = getStr(email, 100);
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = getStr(qq, 20);
	}

	public String getResumeNo() {
		return resumeNo;
	}

	public void setResumeNo(String resumeNo) {
		this.resumeNo = getStr(resumeNo, 50);
	}

	public String getResumeLink() {
		return resumeLink;
	}

	public void setResumeLink(String resumeLink) {
		this.resumeLink = getStr(resumeLink, 200);
	}

	public SimpleSource getSource() {
		return source;
	}

	public void setSource(SimpleSource source) {
		this.source = source;
	}

	public String getIntention() {
		return intention;
	}

	public void setIntention(String intention) {
		this.intention = getStr(intention, 200);
	}

	public Double getExperience() {
		return experience;
	}

	public String getExperienceStr() {
		return experience == null ? "" : String.valueOf(experience).replace(
				".0", "");
	}

	public void setExperience(Double experience) {
		this.experience = experience;
	}

	public String getExpectSalary() {
		return expectSalary;
	}

	public void setExpectSalary(String expectSalary) {
		this.expectSalary = getStr(expectSalary, 50);
	}

	public String getJhReason() {
		return jhReason;
	}

	public void setJhReason(String jhReason) {
		this.jhReason = getStr(jhReason, 5000);
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = getStr(education, 1000);
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = getStr(skill, 5000);
	}

	public String getPrevJob() {
		return prevJob;
	}

	public void setPrevJob(String prevJob) {
		this.prevJob = prevJob;
	}

	public String getPrevProj() {
		return prevProj;
	}

	public void setPrevProj(String prevProj) {
		this.prevProj = prevProj;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = getStr(other, 5000);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isIntentionRed() {
		return intentionRed;
	}

	public void setIntentionRed(boolean intentionRed) {
		this.intentionRed = intentionRed;
	}

	public String getIntentionSpan() {
		if (intentionRed) {
			return "<span style='color:red;'>" + intention + "</span>";
		} else {
			return intention;
		}
	}

	public int getSourceId() {
		return source.getId();
	}

	public int getPostId() {
		return post.getId();
	}

	public String getCreatorName() {
		return creator.getName();
	}

	public String getLongAddTime() {
		return addTime.substring(0, 19);
	}

	public String getShortAddTime() {
		return getLongAddTime().substring(2, 16);
	}

	public Object getCreatorId() {
		return creator.getId();
	}

	public String getPostName() {
		return post.getName();
	}

	public String getSourceName() {
		return source.getName();
	}

	public ChkBox getDownloadedCheckBox() {
		return new ChkBox().setChecked(downloaded);
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getGenderId() {
		return gender.getId();
	}

	public String getGenderName() {
		return gender.getName();
	}

	private String getStr(String str, int len) {
		if (str == null) {
			return null;
		} else {
			if (len > str.length()) {
				len = str.length();
			}
			return str.substring(0, len);
		}
	}
}