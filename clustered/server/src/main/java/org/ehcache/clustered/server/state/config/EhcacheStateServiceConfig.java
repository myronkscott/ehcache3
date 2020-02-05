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

package org.ehcache.clustered.server.state.config;

import com.tc.classloader.CommonComponent;
import org.ehcache.clustered.server.KeySegmentMapper;
import org.ehcache.clustered.server.state.EhcacheStateService;
import org.terracotta.entity.ServiceConfiguration;
import org.terracotta.entity.ServiceRegistry;

@CommonComponent
public class EhcacheStateServiceConfig implements ServiceConfiguration<EhcacheStateService> {

  private final byte[] config;
  private final ServiceRegistry serviceRegistry;
  private final KeySegmentMapper mapper;


  public EhcacheStateServiceConfig(byte[] configBytes, ServiceRegistry serviceRegistry, final KeySegmentMapper mapper) {
    this.config = configBytes;
    this.serviceRegistry = serviceRegistry;
    this.mapper = mapper;
  }

  @Override
  public Class<EhcacheStateService> getServiceType() {
    return EhcacheStateService.class;
  }

  public byte[] getConfig() {
    return config;
  }

  public ServiceRegistry getServiceRegistry() {
    return this.serviceRegistry;
  }

  public KeySegmentMapper getMapper() {
    return mapper;
  }

}
