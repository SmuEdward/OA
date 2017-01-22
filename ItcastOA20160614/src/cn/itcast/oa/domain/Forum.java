package cn.itcast.oa.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * 论坛实体
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name="itcast_forum")
public class Forum {

	private Long id;
	private String name;
	private String description;
	private int position;
	
	private Set<Topic> topics = new HashSet<Topic>();
	private int topicCount;
	private int articleCount;
	private Topic lastTopic;
	
	public int getArticleCount() {
		return articleCount;
	}
	
	public String getDescription() {
		return description;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	@OneToOne
	@JoinColumn(name="lastTopicId")
	public Topic getLastTopic() {
		return lastTopic;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public int getTopicCount() {
		return topicCount;
	}
	
	@OneToMany(mappedBy="forum",fetch=FetchType.EAGER)
	public Set<Topic> getTopics() {
		return topics;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastTopic(Topic lastTopic) {
		this.lastTopic = lastTopic;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	
	
}
