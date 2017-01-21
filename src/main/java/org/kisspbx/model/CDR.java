package org.kisspbx.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class CDR {
	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@Column(nullable=false)
	private Date calldate;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String clid;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String src;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String dst;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String dcontext;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String channel;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String dstchannel;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String lastapp;
	
	@NotNull
	@Size(max=80)
	@Column(nullable=false, length=80)
	private String lastdata;
	
	@NotNull
	@Column(nullable=false)
	private int duration;
	
	@NotNull
	@Column(nullable=false)
	private int billsec;
	
	@NotNull
	@Size(max=45)
	@Column(nullable=false, length=45)
	private String disposition;
	
	@NotNull
	@Column(nullable=false)
	private int amaflags;
	
	@NotNull
	@Size(max=20)
	@Column(nullable=false, length=20)
	private String accountcode;
	
	@NotNull
	@Size(max=255)
	@Column(nullable=false, length=255)
	private String userfield;
	
	@NotNull
	@Size(max=32)
	@Column(nullable=false, length=32)
	private String uniqueid;
	
	@NotNull
	@Size(max=32)
	@Column(nullable=false, length=32)
	private String linkedid;
	
	@NotNull
	@Size(max=32)
	@Column(nullable=false, length=32)
	private String secuence;
	
	@NotNull
	@Size(max=32)
	@Column(nullable=false, length=32)
	private String peeraccount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCalldate() {
		return calldate;
	}

	public void setCalldate(Date calldate) {
		this.calldate = calldate;
	}

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getDcontext() {
		return dcontext;
	}

	public void setDcontext(String dcontext) {
		this.dcontext = dcontext;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDstchannel() {
		return dstchannel;
	}

	public void setDstchannel(String dstchannel) {
		this.dstchannel = dstchannel;
	}

	public String getLastapp() {
		return lastapp;
	}

	public void setLastapp(String lastapp) {
		this.lastapp = lastapp;
	}

	public String getLastdata() {
		return lastdata;
	}

	public void setLastdata(String lastdata) {
		this.lastdata = lastdata;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getBillsec() {
		return billsec;
	}

	public void setBillsec(int billsec) {
		this.billsec = billsec;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public int getAmaflags() {
		return amaflags;
	}

	public void setAmaflags(int amaflags) {
		this.amaflags = amaflags;
	}

	public String getAccountcode() {
		return accountcode;
	}

	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}

	public String getUserfield() {
		return userfield;
	}

	public void setUserfield(String field) {
		this.userfield = field;
	}

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getLinkedid() {
		return linkedid;
	}

	public void setLinkedid(String linkedid) {
		this.linkedid = linkedid;
	}

	public String getSecuence() {
		return secuence;
	}

	public void setSecuence(String secuence) {
		this.secuence = secuence;
	}

	public String getPeeraccount() {
		return peeraccount;
	}

	public void setPeeraccount(String peeraccount) {
		this.peeraccount = peeraccount;
	}
}
