package edu.postech.csed332.homework6;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

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
    public static TreeModel createProjectTreeModel(Project project) {
        // 트리의 루트와 대응되는 유저 오브젝트는 project
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);

        // The visitor to traverse the Java hierarchy and to construct the tree
        final JavaElementVisitor visitor = new JavaElementVisitor() {
            // TODO: add member variaables if necessary

            @Override
            public void visitPackage(PsiPackage pack) {
                // TODO: add a new node to the parent node, and traverse the content of the package
                if (pack.getName().equals("META-INF")) return;

                System.out.println("3. " + pack.getName());
                for (PsiPackage temp1 : pack.getSubPackages()) {
                    System.out.println("7. " + temp1.getName());
                    for (PsiPackage temp2 : temp1.getSubPackages()) {
                        System.out.println("8. " + temp2.getName());
                        for (PsiPackage temp3 : temp2.getSubPackages()) {
                            System.out.println("9. " + temp3.getName());
                            for (PsiClass temp4 : temp3.getClasses()) {
                                System.out.println("10. " + temp4.getName());
                            }

                            for (PsiPackage temp4 : temp3.getSubPackages()) {
                                System.out.println("11. " + temp4.toString());
                            }
                        }
                    }
                }
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(pack.getQualifiedName());
                node.setUserObject(pack);
                root.add(node);
//                if (pack.getName().equals("META-INF")) {
//                    for (PsiPackage temp : pack.getSubPackages()) {
//                        if (temp instanceof PsiPackage) {
//                            visitPackage((PsiPackage) temp);
//                        } else if (temp instanceof PsiClass) {
//                            visitClass((PsiClass) temp);
//                        } else if (temp instanceof PsiMethod) {
//                            visitMethod((PsiMethod) temp);
//                        } else if (temp instanceof PsiField) {
//                            visitField((PsiField) temp);
//                        } else {
//
//                        }
//                    }
//                }
            }

            @Override
            public void visitClass(PsiClass aClass) {
                // TODO: add a new node the parent node, and traverse the content of the class
                System.out.println("4. " + aClass.getName());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(aClass.getQualifiedName());
                node.setUserObject(aClass);
                root.add(node);
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
    private static Set<PsiPackage> getRootPackages(Project project) {
        final Set<PsiPackage> rootPackages = new HashSet<>();
        PsiElementVisitor visitor = new PsiElementVisitor() {
            @Override
            public void visitDirectory(PsiDirectory dir) {
                // PsiDirectory가 visitor의 입력으로 들어오면,
                // 각 directory 하위 package를 읽는다.
                final PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(dir);
                // 패키지가 존재하거나, Default package가 아니면,
                if (psiPackage != null && !PackageUtil.isPackageDefault(psiPackage)) {
                    System.out.println("1. " + psiPackage.getName());
                    rootPackages.add(psiPackage);
                }
                // Print 결과, 모두 null이라서 else 구문이 실행되는데, 네 가지 경우, (왜 네 가지인거야...?)
                // problem2/src/main: null -> edu
                // problem2/src/test: null -> edu
                // null -> META-INF
                // null
                else {
                    System.out.println("2. " + psiPackage.getName());
                    Arrays.stream(dir.getSubdirectories()).forEach(sd -> sd.accept(this));
                }
            }
        };

        // Allows to query and modify the list of root files and directories
        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);

        // The main entry point for accessing the PSI services for a project
        PsiManager psiManager = PsiManager.getInstance(project);

        // rootManager는 problem2 & problem2/src/main & problem2/src/test 세 가지를 가지고 있다.
        Arrays.stream(rootManager.getContentSourceRoots())
                .map(psiManager::findDirectory)             // 세 가지 폴더에 대한 PsiDirectory 반환
                .filter(Objects::nonNull)                   // null인 경우 제외하고
                .forEach(dir -> dir.accept(visitor));       // 각 PsiDirectory에 대해 visitor 함수 적용

        System.out.println("Root Packages: " + rootPackages);
        return rootPackages;
    }
}

