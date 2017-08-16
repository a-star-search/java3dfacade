package com.moduleforge.libraries.java3dfacade;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public abstract class Polygon {

   protected Shape3D shape;

   public Shape3D getShape() {
      return shape;
   }
   
   static Shape3D makeShape(GeometryArray geometryArray, Appearance appearance) {
      GeometryInfo geometryInfo = Polygon.generateNormals(geometryArray);
      GeometryArray front = geometryInfo.getGeometryArray();
      
      return new Shape3D(front, appearance);
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
