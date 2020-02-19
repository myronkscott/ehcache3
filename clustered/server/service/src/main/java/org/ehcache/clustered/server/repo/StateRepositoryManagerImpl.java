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

package org.ehcache.clustered.server.repo;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.terracotta.entity.EntityMessage;
import org.terracotta.entity.EntityResponse;



import static java.util.Collections.emptyList;
import java.util.function.Function;
import org.ehcache.clustered.common.CacheMessage;

public class StateRepositoryManagerImpl implements StateRepositoryManager {

  private final ConcurrentMap<String, ServerStateRepository> mapRepositoryMap = new ConcurrentHashMap<>();

  public StateRepositoryManagerImpl() {
  }

  @Override
  public void destroyStateRepository(String cacheId) {
    mapRepositoryMap.remove(cacheId);
  }

  @Override
  public EntityResponse invoke(CacheMessage message, Function<String, ServerStateRepository> repoCreator) {
    String cacheId = message.getCacheId();
    ServerStateRepository currentRepo = getServerStateRepository(cacheId, repoCreator);
    return currentRepo.invoke(message);
  }

  private ServerStateRepository getServerStateRepository(String cacheId, Function<String, ServerStateRepository> repoCreator) {
    ServerStateRepository currentRepo = mapRepositoryMap.get(cacheId);
    if (currentRepo == null) {
      ServerStateRepository newRepo = repoCreator.apply(cacheId);
      currentRepo = mapRepositoryMap.putIfAbsent(cacheId, newRepo);
      if (currentRepo == null) {
        currentRepo = newRepo;
      }
    }
    return currentRepo;
  }

  @Override
  public List<EntityMessage> syncMessageFor(String cacheId) {
    ServerStateRepository repository = mapRepositoryMap.get(cacheId);
    if (repository != null) {
      return repository.syncMessage(cacheId);
    }
    return emptyList();
  }

  @Override
  public void processSyncMessage(CacheMessage stateRepoSyncMessage, Function<String, ServerStateRepository> repoCreator) {
    getServerStateRepository(stateRepoSyncMessage.getCacheId(), repoCreator).processSyncMessage(stateRepoSyncMessage);
  }
}
