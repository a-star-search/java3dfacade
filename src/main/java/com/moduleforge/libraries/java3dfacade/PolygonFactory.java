package com.moduleforge.libraries.java3dfacade;

import javax.media.j3d.Appearance;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * When making a triangle the order of points determines the direction of the
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

   public static Polygon makeTriangle(Point3d pointA, Point3d pointB, Point3d pointC) {
      return new Triangle(pointA, pointB, pointC);
   }

   public static Polygon makeTriangle(Point3d pointA, Point3d pointB, Point3d pointC, Appearance appearance) {
      return new Triangle(pointA, pointB, pointC, appearance);
   }

   public static Polygon makeTriangle(Point3d pointA, Point3d pointB, Point3d pointC, Color3f color) {
      return new Triangle(pointA, pointB, pointC, color);
   }

   public static Polygon makeQuad(Point3d pointA, Point3d pointB, Point3d pointC, Point3d pointD, Appearance appearance) {
      return new Quad(pointA, pointB, pointC, pointD, appearance);
   }

   public static Polygon makeQuad(Point3d pointA, Point3d pointB, Point3d pointC, Point3d pointD) {
      return new Quad(pointA, pointB, pointC, pointD);
   }
}
