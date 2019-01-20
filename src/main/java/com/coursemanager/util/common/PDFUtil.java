package com.coursemanager.util.common;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author 李如豪
 * @Date 2019/1/17 11:04
 * @VERSION 1.0
 **/
public class PDFUtil {
    private static final Logger logger = LoggerFactory.getLogger(PDFUtil.class);
    public static final String FONT = Objects.requireNonNull(PDFUtil.class.getClassLoader().getResource("")).getPath()+ "static/temp/resource/msyh.ttf";

    public void createPdf(String content, String resources, HttpServletResponse response) {
        try {
            PdfFont msyh = PdfFontFactory.createFont(FONT, false);

            WriterProperties writerProperties = new WriterProperties();
            //Add metadata
            writerProperties.addXmpMetadata();

            PdfWriter pdfWriter = new PdfWriter(response.getOutputStream(), writerProperties);

            PdfDocument pdfDoc = new PdfDocument(pdfWriter);
            pdfDoc.getCatalog().setLang(new PdfString("UTF-8"));
            //Set the document to be tagged
            pdfDoc.setTagged();
            pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));

            //Set meta tags
            PdfDocumentInfo pdfMetaData = pdfDoc.getDocumentInfo();
            pdfMetaData.setAuthor("李如豪");
            pdfMetaData.addCreationDate();
            pdfMetaData.getProducer();
            pdfMetaData.setCreator("李如豪");
            pdfMetaData.setKeywords("resume");
            pdfMetaData.setSubject("PDF resume");
            //Title is derived from html

            //Create event-handlers
            String footer = "浙江理工大学 理学院";
            Footer footerHandler = new Footer(footer,msyh);
//            PageXofY footerHandler = new PageXofY(pdfDoc);

            //Assign event-handlers
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,footerHandler);

            // pdf conversion
            ConverterProperties props = new ConverterProperties();
            FontProvider fp = new FontProvider();
            fp.addFont(msyh.getFontProgram());
            fp.addStandardPdfFonts();
            fp.addDirectory(resources);//The noto-nashk font file (.ttf extension) is placed in the resources

            props.setFontProvider(fp);
            props.setBaseUri(resources);
            //Setup custom tagworker factory for better tagging of headers
            //DefaultTagWorkerFactory tagWorkerFactory = new TagWorkerFactory();
            //props.setTagWorkerFactory(tagWorkerFactory);
            HtmlConverter.convertToPdf(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), pdfDoc, props);
            pdfDoc.close();
        } catch (Exception e) {
            logger.debug("生成PDF文件失败");
            e.printStackTrace();
        }
    }

    //Header event handler
    protected class Header implements IEventHandler {
        String header;
        public Header(String header) {
            this.header = header;
        }
        @Override
        public void handleEvent(Event event) {
            //Retrieve document and
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            canvas.setFontSize(18f);
            canvas.setFont(FONT);
            //Write text at position
            canvas.showTextAligned(header,
                    pageSize.getWidth() / 2,
                    pageSize.getTop() - 30, TextAlignment.CENTER);
        }
    }
    //Header event handler
    protected class Footer implements IEventHandler {
        String footer;
        PdfFont pdfFont;
        public Footer(String footer,PdfFont pdfFont) {
            this.footer = footer;
            this.pdfFont = pdfFont;
        }
        @Override
        public void handleEvent(Event event) {
            //Retrieve document and
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            canvas.setFontSize(8f);
            canvas.setFont(pdfFont);
            //Write text at position
            canvas.showTextAligned(footer,
                    pageSize.getWidth() / 2,
                    pageSize.getBottom() + 30, TextAlignment.CENTER);
        }
    }

    //page X of Y
    protected class PageXofY implements IEventHandler {
        protected PdfFormXObject placeholder;
        protected float side = 20;
        protected float x = 300;
        protected float y = 25;
        protected float space = 4.5f;
        protected float descent = 3;
        public PageXofY(PdfDocument pdf) {
            placeholder =
                    new PdfFormXObject(new Rectangle(0, 0, side, side));
        }
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdf.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            Paragraph p = new Paragraph()
                    .add("Page ").add(String.valueOf(pageNumber)).add(" of");
            canvas.showTextAligned(p, x, y, TextAlignment.RIGHT);
            pdfCanvas.addXObject(placeholder, x + space, y - descent);
            pdfCanvas.release();
        }
        public void writeTotal(PdfDocument pdf) {
            Canvas canvas = new Canvas(placeholder, pdf);
            canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()),
                    0, descent, TextAlignment.LEFT);
        }
    }
}

