package com.bbyn.studentmanagement.util;

import com.bbyn.studentmanagement.model.dto.CourseDetailsDto;
import com.bbyn.studentmanagement.model.dto.CourseSessionDto;
import com.bbyn.studentmanagement.model.dto.StudentSimpleDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generateCoursePdf(CourseDetailsDto courseDetailsDto) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Course Details").setBold().setFontSize(16));
        document.add(new Paragraph("Course Name: " + courseDetailsDto.getName()));
        document.add(new Paragraph("Description: " + courseDetailsDto.getDescription()));
        document.add(new Paragraph("Capacity: " + courseDetailsDto.getCapacity()));

        document.add(new Paragraph("\nCourse Schedule:").setBold().setFontSize(14));
        Table sessionTable = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3}));
        sessionTable.setWidth(UnitValue.createPercentValue(100));

        sessionTable.addHeaderCell("Day of Week");
        sessionTable.addHeaderCell("Start Time");
        sessionTable.addHeaderCell("End Time");

        for (CourseSessionDto courseSession : courseDetailsDto.getCourseSessions()) {
            sessionTable.addCell(courseSession.getDayOfWeek());
            sessionTable.addCell(courseSession.getStartTime());
            sessionTable.addCell(courseSession.getEndTime());
        }

        document.add(sessionTable);

        document.add(new Paragraph("\nStudent List:").setBold().setFontSize(14));
        Table studentTable = new Table(UnitValue.createPercentArray(new float[]{2, 8}));
        studentTable.setWidth(UnitValue.createPercentValue(100));

        studentTable.addHeaderCell("Student ID");
        studentTable.addHeaderCell("Student Name");

        for (StudentSimpleDto studentSimpleDto : courseDetailsDto.getStudents()) {
            studentTable.addCell(studentSimpleDto.getId().toString());
            studentTable.addCell(studentSimpleDto.getUsername());
        }

        document.add(studentTable);

        document.close();

        return out.toByteArray();
    }
}

