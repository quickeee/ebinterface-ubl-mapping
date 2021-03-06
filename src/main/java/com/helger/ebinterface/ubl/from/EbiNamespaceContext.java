/**
 * Copyright (c) 2010-2015 Bundesrechenzentrum GmbH - www.brz.gv.at
 * Copyright (c) 2015-2016 AUSTRIAPRO - www.austriapro.at
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.ebinterface.ubl.from;

import com.helger.ebinterface.CEbInterface;
import com.helger.xml.CXML;
import com.helger.xml.namespace.MapBasedNamespaceContext;

/**
 * A special map-based namespace context that maps XML prefixes to namespace
 * URLs.
 *
 * @author Philip Helger
 */
public class EbiNamespaceContext extends MapBasedNamespaceContext
{
  public EbiNamespaceContext ()
  {
    addMapping ("xsi", CXML.XML_NS_XSI);
    addMapping ("xs", CXML.XML_NS_XSD);
    addMapping ("eb30", CEbInterface.EBINTERFACE_30_NS);
    addMapping ("eb302", CEbInterface.EBINTERFACE_302_NS);
    addMapping ("eb40", CEbInterface.EBINTERFACE_40_NS);
    addMapping ("eb41", CEbInterface.EBINTERFACE_41_NS);
    addMapping ("eb42", CEbInterface.EBINTERFACE_42_NS);
    addMapping ("dsig", "http://www.w3.org/2000/09/xmldsig#");
  }
}
