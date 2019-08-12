package com.jslsolucoes.nginx.admin.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
@Entity
@Table(name = "virtual_host")
@SequenceGenerator(name = "virtual_host_sq", initialValue = 1, allocationSize = 1, sequenceName = "virtual_host_sq")
public class VirtualHost implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "virtual_host_sq")
	private Long id;

	@Column(name = "https")
	private Integer https;

	@Column(name = "queue_size")
	private Long queueSize;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nginx")
	private Nginx nginx;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ssl_certificate")
	private SslCertificate sslCertificate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_resource_identifier")
	private ResourceIdentifier resourceIdentifier;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "virtualHost")
	private Set<VirtualHostAlias> aliases;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "virtualHost")
	private Set<VirtualHostLocation> locations;

	public VirtualHost() {
		// default constructor
	}

	public VirtualHost(Long id) {
		this.id = id;
	}

	public VirtualHost(Long id, Integer https, Long queueSize, SslCertificate sslCertificate,
					   ResourceIdentifier resourceIdentifier, Nginx nginx) {
		this.id = id;
		this.https = https;
		this.queueSize = queueSize;
		this.sslCertificate = sslCertificate;
		this.resourceIdentifier = resourceIdentifier;
		this.nginx = nginx;
	}

	@Deprecated
	public VirtualHost(Long id, Integer https, SslCertificate sslCertificate, ResourceIdentifier resourceIdentifier,
					   Nginx nginx) {
		this.id = id;
		this.nginx = nginx;
		this.https = https == null ? 0 : https;
		this.sslCertificate = sslCertificate;
		this.resourceIdentifier = resourceIdentifier;
	}

	public VirtualHost(Integer https, SslCertificate sslCertificate) {
		this.https = https;
		this.sslCertificate = sslCertificate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getHttps() {
		return https;
	}

	public void setHttps(Integer https) {
		this.https = https;
	}

	public Long getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(Long queueSize) {
		this.queueSize = queueSize;
	}

	public SslCertificate getSslCertificate() {
		return sslCertificate;
	}

	public void setSslCertificate(SslCertificate sslCertificate) {
		this.sslCertificate = sslCertificate;
	}

	public ResourceIdentifier getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(ResourceIdentifier resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	public Set<VirtualHostLocation> getLocations() {
		return locations;
	}

	public Set<VirtualHostAlias> getAliases() {
		return aliases;
	}

	public String getFullAliases() {
		return StringUtils.join(aliases.stream().map(VirtualHostAlias::getAlias).collect(Collectors.toSet()), " ");
	}

	public Nginx getNginx() {
		return nginx;
	}

	public void setNginx(Nginx nginx) {
		this.nginx = nginx;
	}

}
