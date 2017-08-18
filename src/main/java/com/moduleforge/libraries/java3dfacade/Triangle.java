package com.moduleforge.libraries.java3dfacade;

import java.util.Arrays;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * Triangle
 *
 */
class Triangle extends Polygon {

   Triangle(Point3d pointA, Point3d pointB, Point3d pointC) {
      this(pointA, pointB, pointC, Polygon.makeDefaultAppearance());
   }
   
   Triangle(Point3d pointA, Point3d pointB, Point3d pointC, Appearance appearance) {
      super(Arrays.asList(new Point3d[] {pointA, pointB, pointC}));
      TriangleArray triangle = new TriangleArray(3, GeometryArray.COORDINATES);
      triangle.setCoordinate(0, pointA);
      triangle.setCoordinate(1, pointB);
      triangle.setCoordinate(2, pointC);
 
      shape = makeShape(triangle, appearance);
   }

   public Triangle(Point3d pointA, Point3d pointB, Point3d pointC, Color3f color) {
      this(pointA, pointB, pointC, Polygon.makeAppearance(Polygon.makeMaterial(color)));
   }
   
}
