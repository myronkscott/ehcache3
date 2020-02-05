/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ehcache.clustered.server.state;

import org.ehcache.clustered.common.internal.exceptions.ClusterException;
import org.ehcache.clustered.server.ServerSideServerStore;
import org.ehcache.clustered.server.repo.StateRepositoryManager;
import org.terracotta.entity.ConfigurationException;

import com.tc.classloader.CommonComponent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import org.ehcache.clustered.common.ServerResourcePool;

@CommonComponent
public interface EhcacheStateService {

  String getDefaultServerResource();

  Map<String, ServerResourcePool> getSharedResourcePools();

  Object getSharedResourcePageSource(String name);

  ServerResourcePool getDedicatedResourcePool(String name);

  Object getDedicatedResourcePageSource(String name);

  ServerSideServerStore getStore(String name);

  ServerSideServerStore loadStore(String name, byte[] serverStoreConfiguration);

  Set<String> getStores();

  void prepareForDestroy();

  void destroy();

  void validate(byte[] configuration) throws ClusterException;

  void configure() throws ConfigurationException;

  ServerSideServerStore createStore(String name, byte[] serverStoreConfiguration, boolean forActive) throws ConfigurationException;

  void destroyServerStore(String name) throws ClusterException;

  boolean isConfigured();

  StateRepositoryManager getStateRepositoryManager();

  InvalidationTracker getInvalidationTracker(String name);

  void loadExisting(byte[] config);

  EhcacheStateContext beginProcessing(Callable<Boolean> isBeingTracked, String name);
}
