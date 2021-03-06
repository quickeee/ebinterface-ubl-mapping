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
package com.helger.ebinterface.ubl.from.invoice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.error.level.EErrorLevel;
import com.helger.commons.error.list.ErrorList;
import com.helger.commons.io.file.FileHelper;
import com.helger.commons.io.file.FilenameHelper;
import com.helger.commons.io.file.filter.IFileFilter;
import com.helger.commons.io.file.iterate.FileSystemIterator;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.ebinterface.ubl.from.Ebi42TestMarshaller;
import com.helger.ebinterface.v42.Ebi42InvoiceType;
import com.helger.ubl21.UBL21Reader;

import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

/**
 * Test class for class {@link InvoiceToEbInterface42Converter}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class InvoiceToEbInterface42ConverterTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (InvoiceToEbInterface42ConverterTest.class);

  @Test
  public void testConvertPEPPOLInvoiceLax ()
  {
    final ICommonsList <IReadableResource> aTestFiles = new CommonsArrayList<> ();
    for (final File aFile : new FileSystemIterator (new File ("src/test/resources/ubl20/invoice")).withFilter (IFileFilter.filenameEndsWith (".xml")))
      aTestFiles.add (new FileSystemResource (aFile));

    // For all PEPPOL test invoices
    for (final IReadableResource aRes : aTestFiles)
    {
      s_aLogger.info (aRes.getPath ());
      assertTrue (aRes.exists ());

      // Read UBL
      final InvoiceType aUBLInvoice = UBL21Reader.invoice ().read (aRes);
      assertNotNull (aUBLInvoice);

      // Convert to ebInterface
      final ErrorList aErrorList = new ErrorList ();
      final Ebi42InvoiceType aEbInvoice = new InvoiceToEbInterface42Converter (Locale.GERMANY,
                                                                               Locale.GERMANY,
                                                                               false).convertToEbInterface (aUBLInvoice,
                                                                                                            aErrorList);
      assertTrue (aRes.getPath () +
                  ": " +
                  aErrorList.toString (),
                  aErrorList.isEmpty () || aErrorList.getMostSevereErrorLevel ().isLessSevereThan (EErrorLevel.ERROR));
      assertNotNull (aEbInvoice);

      if (!aErrorList.isEmpty () && aErrorList.getMostSevereErrorLevel ().isMoreOrEqualSevereThan (EErrorLevel.WARN))
        s_aLogger.info ("  " + aErrorList.getAllItems ());

      // Convert ebInterface to XML
      assertTrue (new Ebi42TestMarshaller ().write (aEbInvoice,
                                                    FileHelper.getOutputStream ("generated-ebi42-files/" +
                                                                                FilenameHelper.getWithoutPath (aRes.getPath ())))
                                            .isSuccess ());
    }
  }

  @Test
  public void testConvertPEPPOLInvoiceERB ()
  {
    final ICommonsList <IReadableResource> aTestFiles = new CommonsArrayList<> ();
    for (final File aFile : new FileSystemIterator (new File ("src/test/resources/ubl20/invoice")).withFilter (IFileFilter.filenameEndsWith (".xml")))
      aTestFiles.add (new FileSystemResource (aFile));

    // For all PEPPOL test invoices
    for (final IReadableResource aRes : aTestFiles)
    {
      s_aLogger.info (aRes.getPath ());
      assertTrue (aRes.exists ());

      // Read UBL
      final InvoiceType aUBLInvoice = UBL21Reader.invoice ().read (aRes);
      assertNotNull (aUBLInvoice);

      // Convert to ebInterface
      final ErrorList aErrorList = new ErrorList ();
      final Ebi42InvoiceType aEbInvoice = new InvoiceToEbInterface42Converter (Locale.GERMANY,
                                                                               Locale.GERMANY,
                                                                               true).convertToEbInterface (aUBLInvoice,
                                                                                                           aErrorList);
      assertTrue (aRes.getPath () +
                  ": " +
                  aErrorList.toString (),
                  aErrorList.getMostSevereErrorLevel ().isLessSevereThan (EErrorLevel.ERROR));
      assertNotNull (aEbInvoice);

      if (aErrorList.getMostSevereErrorLevel ().isMoreOrEqualSevereThan (EErrorLevel.WARN))
        s_aLogger.info ("  " + aErrorList.getAllItems ());

      // Convert ebInterface to XML
      assertTrue (new Ebi42TestMarshaller ().write (aEbInvoice,
                                                    FileHelper.getOutputStream ("generated-ebi42-files/" +
                                                                                FilenameHelper.getWithoutPath (aRes.getPath ())))
                                            .isSuccess ());
    }
  }

  @Test
  public void testConvertPEPPOLInvoiceLaxBad ()
  {
    final ICommonsList <IReadableResource> aTestFiles = new CommonsArrayList<> ();
    for (final File aFile : new FileSystemIterator (new File ("src/test/resources/ubl20/invoice_bad")).withFilter (IFileFilter.filenameEndsWith (".xml")))
      aTestFiles.add (new FileSystemResource (aFile));

    // For all PEPPOL test invoices
    for (final IReadableResource aRes : aTestFiles)
    {
      s_aLogger.info (aRes.getPath ());
      assertTrue (aRes.exists ());

      // Read UBL
      final InvoiceType aUBLInvoice = UBL21Reader.invoice ().read (aRes);
      assertNotNull (aUBLInvoice);

      // Convert to ebInterface
      final ErrorList aErrorList = new ErrorList ();
      final Ebi42InvoiceType aEbInvoice = new InvoiceToEbInterface42Converter (Locale.GERMANY,
                                                                               Locale.GERMANY,
                                                                               false).convertToEbInterface (aUBLInvoice,
                                                                                                            aErrorList);
      assertNotNull (aEbInvoice);
      assertTrue (aRes.getPath () + ": " + aErrorList.toString (), !aErrorList.isEmpty () &&
                                                                   aErrorList.getMostSevereErrorLevel ()
                                                                             .isMoreOrEqualSevereThan (EErrorLevel.ERROR));

      // Convert ebInterface to XML
      final Document aDocEb = new Ebi42TestMarshaller ().getAsDocument (aEbInvoice);
      assertNull (aDocEb);
    }
  }
}
