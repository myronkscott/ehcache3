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
import java.util.function.Function;
import org.ehcache.clustered.common.CacheMessage;
import org.terracotta.entity.EntityMessage;
import org.terracotta.entity.EntityResponse;

public interface StateRepositoryManager {

  void destroyStateRepository(String cacheId);

  EntityResponse invoke(CacheMessage message, Function<String, ServerStateRepository> repoCreator);

  List<EntityMessage> syncMessageFor(String cacheId);

  void processSyncMessage(CacheMessage stateRepoSyncMessage, Function<String, ServerStateRepository> repoCreator);
}
