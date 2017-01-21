package org.kisspbx.model.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.kisspbx.model.feature.Queue;

@Entity
@DiscriminatorValue("QUEUE")
public class QueueApplication extends Application {
	
	@ManyToOne
	private Queue queue;
	
	public QueueApplication() {
		extension = "s";
		priority = "1";
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}
	
	@Override
	public String getApplication() {
		return "queue-feature";
	}
	
	@Override
	public List<String> getAppVariables() {
		List<String> vars = new ArrayList<>();
		vars.add("QUEUE_NAME=" + queue.getName());
		return vars;
	}
}
