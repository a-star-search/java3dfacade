package com.moduleforge.libraries.java3dfacade;

import static com.moduleforge.libraries.java3dfacade.PolygonFactory.makePolygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;

import org.javatuples.Pair;

import com.google.common.base.Preconditions;
import com.moduleforge.libraries.geometry.GeometryUtil;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.picking.PickTool;

public abstract class Polygon {

   protected Shape3D shape;

   /**
    * Points do have an order that determines the face direction
    */
   protected List<Point3d> _points;
   
   private transient Set<Pair<Point3d, Point3d>> _segments;
   
   protected Polygon(List<Point3d> points) {
      _points = points;
      _segments = makeSegments(_points);
   }
   
   public Shape3D getShape() {
      return shape;
   }
   
   public List<Point3d> getPoints(){
      return Collections.unmodifiableList(_points);
   }
   
   public Set<Pair<Point3d, Point3d>> getSegments(){
      return Collections.unmodifiableSet(_segments);
   }

   private static Set<Pair<Point3d, Point3d>> makeSegments(List<Point3d> points) {
      Set<Pair<Point3d, Point3d>> segments = new HashSet<>();
      Point3d previousPoint = points.get(points.size() - 1);
      for(Point3d point : points) {
         Pair<Point3d, Point3d> segment = new Pair<>(previousPoint, point);
         segments.add(segment);  
         previousPoint = point;
      }
      return segments;
   }

   static Shape3D makeShape(GeometryArray geometryArray, Appearance appearance) {
      GeometryInfo geometryInfo = Polygon.generateNormals(geometryArray);
      GeometryArray front = geometryInfo.getGeometryArray();
      Shape3D shape3d = new Shape3D(front, appearance);

      shape3d.setCapability(Node.ENABLE_PICK_REPORTING);
      PickTool.setCapabilities(shape3d, PickTool.INTERSECT_FULL);
      return shape3d;
   }
   
   static GeometryInfo generateNormals(GeometryArray ga) {
      GeometryInfo geometryInfo = new GeometryInfo(ga);
      NormalGenerator ng = new NormalGenerator();
      ng.generateNormals(geometryInfo);
      return geometryInfo;
   }

   static Appearance makeDefaultAppearance() {
      return makeAppearance(makeDefaultMaterial());
   }

   static Appearance makeAppearance(Material material) {
      
      Texture texture = new Texture2D();
      TextureAttributes texAttr = new TextureAttributes();
      texAttr.setTextureMode(TextureAttributes.MODULATE);
      texture.setBoundaryModeS(Texture.WRAP);
      texture.setBoundaryModeT(Texture.WRAP);
      texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

      Appearance appearance = new Appearance();      
      appearance.setTextureAttributes(texAttr);
      appearance.setMaterial(material);
      appearance.setTexture(texture);
      
      return appearance;
   }

   static Material makeDefaultMaterial() {
      Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
      Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
      Material mat = new Material(white, black, white, white, 70f);
      return mat;
   }

   static Material makeMaterial(Color3f color) {
      Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
      Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
      Material mat = new Material(color, black, color, white, 70f);
      return mat;
   }

   /**
    * The faces created are all connected to each other.
    * 
    * All the vertices must lay on the same plane. They must also have an ordering,
    * so that the direction of the face can be stablished. As you can suppose all
    * faces created in this way should face the same direction
    * 
    */
   public static List<Polygon> polygonsFromPointsOnAPlane(List<Point3d> pointsOnPlane) {

      Preconditions.checkNotNull(pointsOnPlane);
      Preconditions.checkArgument(pointsOnPlane.size() >= 3, "There should be a minimum of three vertices.");
      Preconditions.checkArgument(allPointsAreDifferentEnough(pointsOnPlane), "Some of the vertices are equal or almost equal.");
      Preconditions.checkArgument(GeometryUtil.inSamePlane(pointsOnPlane), "The vertices do not lay on a plane.");

      List<Point3d> pointsCopy = new ArrayList<>();
      pointsCopy.addAll(pointsOnPlane);
      List<Polygon> polygons = new ArrayList<>();
      polygonsFromPointsOnAPlaneRecursive(pointsCopy, polygons);
      return polygons;
   }
   
   private static boolean allPointsAreDifferentEnough(List<Point3d> points) {

      for (int i = 0; i < points.size(); i++) {
         for (int j = i + 1; j < points.size(); j++) {
            Point3d outerLoopVertex = points.get(i);
            Point3d innerLoopVertex = points.get(j);
            if (!GeometryUtil.differentEnough(outerLoopVertex, innerLoopVertex)) {
               return false;
            }
         }
      }
      return true;
   }

   private static void polygonsFromPointsOnAPlaneRecursive(List<Point3d> points, List<Polygon> polygons) {
      List<Point3d> pointsNewPolygon = makePointListNextPolygon(points);
      polygons.add(makePolygon(pointsNewPolygon));
      boolean morePolygons = points.size() > 4; 
      if (morePolygons) {
         cullPointList(points, pointsNewPolygon.size());
         polygonsFromPointsOnAPlaneRecursive(points, polygons);
      }
   }

   private static List<Point3d> makePointListNextPolygon(List<Point3d> points) {
      //a polygon is made of either three or four elements
      List<Point3d> pointsNewPolygon =  (points.size() == 3) ?
            Arrays.asList(new Point3d[] { points.get(0), points.get(1), points.get(2) }) :
            Arrays.asList(new Point3d[] { points.get(0), points.get(1), points.get(2), points.get(3) });
      return pointsNewPolygon;
   }

   private static void cullPointList(List<Point3d> points, int pointCountOfLastPolygon) {
      int fromIndex = 1; //the first element is not removed, as it is part of the next polygon
      int elementCountToRemove = (pointCountOfLastPolygon == 3) ? 1 : 2; //remove one if a triangle was made, two if it was a quad
      int toIndex = fromIndex + elementCountToRemove;
      points.subList(fromIndex, toIndex).clear();
   }

}
