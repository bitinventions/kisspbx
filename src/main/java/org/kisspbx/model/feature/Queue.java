package org.kisspbx.model.feature;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Size;

@Entity
public class Queue {
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Size(max=64)
	@Column(nullable=false, unique=true, length=64)
	private String name;
	
	@Size(max=255)
	@Column(length=255)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private QueueStrategy strategy;
	
	private boolean joinempty = true;
	
	private boolean leavewhenempty = false;
	
	private int maxlen = 0;
	
	private int timeout = 15;
	
	private int retry = 5;
	
	private int wrapuptime = 5;
	
	private boolean ringinuse = false;
	
	private String music = "default";
	
	private int weight = 0;
	
	@Column(length=512)
	private String members;
	
	// announcements
	private String announcefrequency;
	
	private String minannouncefrequency;
	
	private String periodicannouncefrequency;
	
	private String randomperiodicannounce;
	
	private String relativeperiodicannounce;
	
	private String announceholdtime;
	
	@Column(length=2048)
    private String parameters;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public QueueStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(QueueStrategy strategy) {
		this.strategy = strategy;
	}

	public boolean isJoinempty() {
		return joinempty;
	}

	public void setJoinempty(boolean joinempty) {
		this.joinempty = joinempty;
	}

	public boolean isLeavewhenempty() {
		return leavewhenempty;
	}

	public void setLeavewhenempty(boolean leavewhenempty) {
		this.leavewhenempty = leavewhenempty;
	}

	public int getMaxlen() {
		return maxlen;
	}

	public void setMaxlen(int maxlen) {
		this.maxlen = maxlen;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public int getWrapuptime() {
		return wrapuptime;
	}

	public void setWrapuptime(int wrapuptime) {
		this.wrapuptime = wrapuptime;
	}

	public boolean isRinginuse() {
		return ringinuse;
	}

	public void setRinginuse(boolean ringinuse) {
		this.ringinuse = ringinuse;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getAnnouncefrequency() {
		return announcefrequency;
	}

	public void setAnnouncefrequency(String announcefrequency) {
		this.announcefrequency = announcefrequency;
	}

	public String getMinannouncefrequency() {
		return minannouncefrequency;
	}

	public void setMinannouncefrequency(String minannouncefrequency) {
		this.minannouncefrequency = minannouncefrequency;
	}

	public String getPeriodicannouncefrequency() {
		return periodicannouncefrequency;
	}

	public void setPeriodicannouncefrequency(String periodicannouncefrequency) {
		this.periodicannouncefrequency = periodicannouncefrequency;
	}

	public String getRandomperiodicannounce() {
		return randomperiodicannounce;
	}

	public void setRandomperiodicannounce(String randomperiodicannounce) {
		this.randomperiodicannounce = randomperiodicannounce;
	}

	public String getRelativeperiodicannounce() {
		return relativeperiodicannounce;
	}

	public void setRelativeperiodicannounce(String relativeperiodicannounce) {
		this.relativeperiodicannounce = relativeperiodicannounce;
	}

	public String getAnnounceholdtime() {
		return announceholdtime;
	}

	public void setAnnounceholdtime(String announceholdtime) {
		this.announceholdtime = announceholdtime;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return name;
	}
}
