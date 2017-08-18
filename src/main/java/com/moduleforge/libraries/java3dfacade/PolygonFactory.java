package com.moduleforge.libraries.java3dfacade;

import java.util.List;

import javax.media.j3d.Appearance;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * When making a polygon (triangle or quad) the order of points determines the direction of the
 * face.
 * 
 * In order means that the counterclockwise direction of the vertices indicate
 * the side of the face
 * 
 * It doesn't really matter what element goes in which position of the list,
 * only the relative ordering. It should be understood as a circular list.
 * 
 */
public class PolygonFactory {

   public static Polygon makePolygon(List<Point3d> points) {
      return makePolygon(points.toArray(new Point3d[points.size()]) );
   }
   
   public static Polygon makePolygon(Appearance appearance, List<Point3d> points) {
      return makePolygon(appearance, points.toArray(new Point3d[points.size()]) );
   }
   
   public static Polygon makePolygon(Color3f color, List<Point3d> points) {
      return makePolygon(color, points.toArray(new Point3d[points.size()]) );
   }
   
   public static Polygon makePolygon(Point3d... points) {
      if(points.length < 3 || points.length > 4) {
         throw new IllegalArgumentException("Wrong number of points.");
      }
      if(points.length == 3) {
         return new Triangle(points[0], points[1], points[2]);
      }
      return new Quad(points[0], points[1], points[2], points[3]);
   }

   public static Polygon makePolygon(Appearance appearance, Point3d... points) {
      if(points.length < 3 || points.length > 4) {
         throw new IllegalArgumentException("Wrong number of points.");
      }
      if(points.length == 3) {
         return new Triangle(points[0], points[1], points[2], appearance);
      }
      return new Quad(points[0], points[1], points[2], points[3], appearance);
   }

   public static Polygon makePolygon(Color3f color, Point3d... points) {
      if(points.length < 3 || points.length > 4) {
         throw new IllegalArgumentException("Wrong number of points.");
      }
      if(points.length == 3) {
         return new Triangle(points[0], points[1], points[2], color);
      }
      return new Quad(points[0], points[1], points[2], points[3], color);
   }
   
   public static Polygon makeTriangle(Point3d pointA, Point3d pointB, Point3d pointC) {
      return new Triangle(pointA, pointB, pointC);
   }

   public static Polygon makeTriangle(Appearance appearance, Point3d pointA, Point3d pointB, Point3d pointC) {
      return new Triangle(pointA, pointB, pointC, appearance);
   }

   public static Polygon makeTriangle(Color3f color, Point3d pointA, Point3d pointB, Point3d pointC) {
      return new Triangle(pointA, pointB, pointC, color);
   }

   public static Polygon makeQuad(Appearance appearance, Point3d pointA, Point3d pointB, Point3d pointC, Point3d pointD) {
      return new Quad(pointA, pointB, pointC, pointD, appearance);
   }

   public static Polygon makeQuad(Point3d pointA, Point3d pointB, Point3d pointC, Point3d pointD) {
      return new Quad(pointA, pointB, pointC, pointD);
   }
}
