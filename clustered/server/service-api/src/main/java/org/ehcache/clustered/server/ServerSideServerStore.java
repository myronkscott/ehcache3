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
package org.ehcache.clustered.server;

import org.ehcache.clustered.server.store.Chain;
import org.ehcache.clustered.server.store.ServerStore;

import java.util.List;
import java.util.Set;

public interface ServerSideServerStore extends ServerStore {
  void setEventListener(ServerStoreEventListener listener);
  void enableEvents(boolean enable);
  byte[] getStoreConfiguration();
  List<Set<Long>> getSegmentKeySets();
  void put(long key, Chain chain);
  void remove(long key);

  /* from map internals */

  long getSize();

  long getTableCapacity();

  long getUsedSlotCount();

  long getRemovedSlotCount();

  int getReprobeLength();

  long getAllocatedMemory();

  long getOccupiedMemory();

  long getVitalMemory();

  long getDataAllocatedMemory();

  long getDataOccupiedMemory();

  long getDataVitalMemory();

  long getDataSize();
}
