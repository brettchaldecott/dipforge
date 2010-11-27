package thewebsemantic.binding;

import java.rmi.server.UID;

import thewebsemantic.Id;

public class RdfBeanId<T> extends RdfBean<T> {

	protected String id;

	public RdfBeanId() {
		this.id = new UID().toString();
	}

	public RdfBeanId(String id) {
		this.id = id;
	}

	@Id
	public String id() {return id;}

}
