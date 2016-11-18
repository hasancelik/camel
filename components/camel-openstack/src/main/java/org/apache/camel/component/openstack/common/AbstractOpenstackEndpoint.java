/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.openstack.common;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultEndpoint;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.client.IOSClientBuilder;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;

public abstract class AbstractOpenstackEndpoint extends DefaultEndpoint {

	protected abstract String getHost();

	protected abstract String getUsername();

	protected abstract String getDomain();

	protected abstract String getPassword();

	protected abstract String getProject();

	protected abstract String getOperation();

	public AbstractOpenstackEndpoint(String endpointUri, Component component) {
		super(endpointUri, component);
	}

	protected OSClient.OSClientV3 createClient() {

		//client sholud reAuthenticate itself when token expires
		IOSClientBuilder.V3 builder = OSFactory.builderV3()
				.endpoint(getHost());

		builder.credentials(getUsername(), getPassword(), Identifier.byId(getDomain()));

		builder.scopeToProject(Identifier.byId(getProject()));
		return builder.authenticate();
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		throw new IllegalStateException("There is no consumer available for OpenStack");
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}