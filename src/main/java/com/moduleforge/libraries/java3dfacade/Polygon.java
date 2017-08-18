package com.moduleforge.libraries.java3dfacade;

import java.util.Collections;
import java.util.List;

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

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.picking.PickTool;

public abstract class Polygon {

   protected Shape3D shape;

   /**
    * Points do have an order that determines the face direction
    */
   protected List<Point3d> points;
   
   protected Polygon(List<Point3d> points) {
      this.points = points;
   }
   
   public Shape3D getShape() {
      return shape;
   }
   
   public List<Point3d> getPoints(){
      return Collections.unmodifiableList(points);
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


}
