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
	private double experience;
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
		this.company = company;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
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
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getResumeNo() {
		return resumeNo;
	}

	public void setResumeNo(String resumeNo) {
		this.resumeNo = resumeNo;
	}

	public String getResumeLink() {
		return resumeLink;
	}

	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
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
		this.intention = intention;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public String getExpectSalary() {
		return expectSalary;
	}

	public void setExpectSalary(String expectSalary) {
		this.expectSalary = expectSalary;
	}

	public String getJhReason() {
		return jhReason;
	}

	public void setJhReason(String jhReason) {
		this.jhReason = jhReason;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
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
		this.other = other;
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

}