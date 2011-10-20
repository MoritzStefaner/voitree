package toxi.test;

import toxi.geom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class JAXBGeomTest {

    private static final String XML_FILE = "test/geomtypes.xml";

    private static JAXBGeomTest load() {
        try {
            JAXBContext context = JAXBContext.newInstance(JAXBGeomTest.class);
            File file = new File(XML_FILE);
            JAXBGeomTest test = (JAXBGeomTest) context.createUnmarshaller()
                    .unmarshal(file);
            return test;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        save();
        JAXBGeomTest test = load();
        System.out.println(test.spline2d.pointList.size());
    }

    private static void save() {
        try {
            JAXBGeomTest test = new JAXBGeomTest();
            test.box = new AABB();
            test.plane = new Plane();
            test.quat = new Quaternion(0, Vec3D.X_AXIS);
            test.ray = new Ray3D();
            test.rect = new Rect(0, 0, 100, 200);
            test.sphere = new Sphere();
            test.tri = new Triangle3D(new Vec3D(), new Vec3D(), new Vec3D());
            List<Vec2D> points2d = new ArrayList<Vec2D>();
            points2d.add(new Vec2D());
            points2d.add(new Vec2D());
            points2d.add(new Vec2D());
            points2d.add(new Vec2D());
            test.spline2d = new Spline2D(points2d);
            List<Vec3D> points = new ArrayList<Vec3D>();
            points.add(new Vec3D());
            points.add(new Vec3D());
            points.add(new Vec3D());
            points.add(new Vec3D());
            test.spline3d = new Spline3D(points);
            JAXBContext context = JAXBContext.newInstance(JAXBGeomTest.class);
            File file = new File(XML_FILE);
            context.createMarshaller().marshal(test, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @XmlElement
    Spline2D spline2d;

    @XmlElement
    Spline3D spline3d;

    @XmlElement
    AABB box;

    @XmlElement
    Ray3D ray;

    @XmlElement
    Sphere sphere;

    @XmlElement
    Quaternion quat;

    @XmlElement
    Plane plane;

    @XmlElement
    Triangle3D tri;

    @XmlElement
    Rect rect;

}
