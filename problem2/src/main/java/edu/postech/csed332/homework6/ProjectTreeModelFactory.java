package edu.postech.csed332.homework6;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class ProjectTreeModelFactory {

    /**
     * Create a tree model that describes the structure of a java project. This method use JavaElementVisitor to
     * traverse the Java hierarchy of each root package in the source directory, and to create a tree. Each node is an
     * instance of {@link DefaultMutableTreeNode} that can have a user object. The user object of root is the project
     * itself, and other nodes have corresponding instances of PsiPackage, PsiClass, PsiMethod, and PsiField.
     *
     * 자바 프로젝트의 트리모델을 만드는 메소드.
     * 트리의 모든 노드는 DefaultMutableTreeNode의 인스턴스
     * 그 노드와 연결된 user object는 Psipackage, PsiClass.. 의 인스턴스
     *
     * @param project a project
     * @return a tree model to describe the structure of project
     *
     * 프로젝트 이름은 Project 객체를 통해 알 수 있다 --> project.getName()
     * DefaultMutableTreeNode 객체에서 정의하면서 자동으로 입력된다.
     *
     */

    // TreeModel을 만들어서 리턴
    public static TreeModel createProjectTreeModel(Project project) {
        // 트리의 루트와 대응되는 유저 오브젝트는 project
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);

        // 패키지, 클래스, 메소드, 필드를 traverse하는 visitor
        // The visitor to traverse the Java hierarchy and to construct the tree
        final JavaElementVisitor visitor = new JavaElementVisitor() {
            // TODO: add member variaables if necessary
            @Override
            public void visitPackage(PsiPackage pack) {
                // TODO: add a new node to the parent node, and traverse the content of the package
//                System.out.println("3. " + pack.getName());
//                for (PsiPackage tmp : pack.getSubPackages()) {
//                    System.out.println("7. " + tmp.getName());
//                }
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(pack.getQualifiedName());
                node.setUserObject(pack);
                root.add(node);
                if (pack.getName().equals("META-INF")) {
                    for (PsiPackage temp : pack.getSubPackages()) {
                        if (temp instanceof PsiPackage) {
                            visitPackage((PsiPackage) temp);
                        } else if (temp instanceof PsiClass) {
                            visitClass((PsiClass) temp);
                        } else if (temp instanceof PsiMethod) {
                            visitMethod((PsiMethod) temp);
                        } else if (temp instanceof PsiField) {
                            visitField((PsiField) temp);
                        } else {

                        }
                    }
                }
            }

            @Override
            public void visitClass(PsiClass aClass) {
                // TODO: add a new node the parent node, and traverse the content of the class
                System.out.println("4. " + aClass.getName());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(aClass.getQualifiedName());
                node.setUserObject(aClass);
                // 클래스의 parent 노드에 넣어줘야 계층도가 만들어지지 않을까?
//                node.getParent()

            }

            @Override
            public void visitMethod(PsiMethod method) {
                // TODO: add a new node to the parent node
                System.out.println("5. " + method.getName());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(method.getName());
                node.setUserObject(method);
                root.add(node);
            }

            @Override
            public void visitField(PsiField field) {
                // TODO: add a new node to the parent node
                System.out.println("6. " + field.getName());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(field.getName());
                node.setUserObject(field);
                root.add(node);
            }
        };

        // apply the visitor for each root package in the source directory
        getRootPackages(project).forEach(aPackage -> aPackage.accept(visitor));
        return new DefaultTreeModel(root);
    }

    /**
     * Returns the root package(s) in the source directory of a project. The default package will not be considered, as
     * it includes all Java classes. Note that classes in the default package (i.e., having no package statement) will
     * be ignored for this assignment. To be completed, this case must be separately handled.
     *
     * @param project a project
     * @return a set of root packages
     *
     * PSI(Program Structure Interface) file is the root of a structure
     * To iterate over the elements in a file, use psiFile.accept(new PsiRecursiveElementWalkingVisitor()...);
     */
    // 루트 패키지의 집합을 리턴하는 메소드 (패키지 ~= 디렉토리 ~= 클래스 모음)
    private static Set<PsiPackage> getRootPackages(Project project) {
        final Set<PsiPackage> rootPackages = new HashSet<>(); // 나중에 이걸 return할 것임

        // 1. VISITOR : PSI element를 visit하는 일반적인 visitor를 패키지에 맞게 오버라이딩해서 사용
        PsiElementVisitor visitor = new PsiElementVisitor() {

            // 디렉토리를 방문하는 메소드 (디렉토리 = 패키지 or 디폴트패키지)
            @Override
            public void visitDirectory(PsiDirectory dir) {
                // getPackage 메소드로 dir 하위의 패키지를 모두 받아온다
                final PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(dir);

                // 디폴트가 아닌 패키지이면 -> rootPackage에 넣는다
                if (psiPackage != null && !PackageUtil.isPackageDefault(psiPackage)) {
                    System.out.println("1. " + psiPackage.getName());
                    System.out.println("1. " + psiPackage.getQualifiedName());
                    rootPackages.add(psiPackage);
                }

                // 디폴트 패키지이면 -> 패키지 안을 계속 검사해서 디폴트가 아닌 패키지가 있는지 찾아본다
                else {
                    // getName과 getQualifiedName의 차이
                        // getQualifiedName은 디폴트가 아닌 패키지의 이름만 받아온다.
                        // 디폴트패키지.getQualifiedName은 null을 리턴
                    System.out.println("2. " + psiPackage.getName());
                    System.out.println("2. " + psiPackage.getQualifiedName());
                    Arrays.stream(dir.getSubdirectories()).forEach(sd -> sd.accept(this));
                }
            }
        };



        // 2. VISITOR가 방문할 모든 디렉토리를 찾아 VISITOR를 넘김
            // '루트 매니저' : project를 넘겨서 파일, 폴더에 접근할 수 있는 권한이 있음
            // Allows to query and modify the list of root files and directories
        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
            // '프사이 매니저' : PSI 오브젝트에 접근할 수 있는 매니저
            // The main entry point for accessing the PSI services for a project
        PsiManager psiManager = PsiManager.getInstance(project);

        // Problem2에서 바라본 content source root는 3개
            // problem2
            // problem2/src/main
            // problem2/src/test
        Arrays.stream(rootManager.getContentSourceRoots())  // VirtualFile 타입으로 리턴
                .map(psiManager::findDirectory)             // VirtualFile에 해당하는 PsiDirectory 리턴
                .filter(Objects::nonNull)                   // null 제외
                .forEach(dir -> dir.accept(visitor));       // 각 PsiDirectory에 대해 visitor 함수 적용

        return rootPackages;
    }
}

