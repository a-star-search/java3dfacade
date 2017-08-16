package com.moduleforge.libraries.java3dfacade;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.vecmath.Point3d;

class Quad extends Polygon {
   
   Quad (Point3d pointA, Point3d pointB, Point3d pointC, Point3d pointD, Appearance appearance) {

      GeometryArray face;
      face = new QuadArray(4, GeometryArray.COORDINATES);
      face.setCoordinate(0, pointA);
      face.setCoordinate(1, pointB);
      face.setCoordinate(2, pointC);
      face.setCoordinate(3, pointD);
      shape = makeShape(face, appearance);
   }

   Quad (Point3d pointA, Point3d pointB, Point3d pointC, Point3d pointD) {
      this(pointA, pointB, pointC, pointD, Polygon.makeDefaultAppearance());
   }
}
