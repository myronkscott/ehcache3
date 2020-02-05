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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class should not exist.  It only exists because of laziness in separating
 * Service and Entity.  This class should disappear with remodularization
 */
public class ConfigSerializer {
//  I got lazy here and just used serialization to get across the
//  interface barrier.  A real re-packaging should be done.
  public static byte[] objectToBytes(Serializable lazy) {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutput out = new ObjectOutputStream(bos))
    {
      out.writeObject(lazy);
      out.flush();
      return bos.toByteArray();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public static Serializable bytesToObject(byte[] lazy) {
    try (ByteArrayInputStream bis = new ByteArrayInputStream(lazy);
      ObjectInput out = new ObjectInputStream(bis)) {
      return (Serializable)out.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
