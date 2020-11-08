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
     * @param project a project
     * @return a tree model to describe the structure of project
     *
     * 프로젝트 이름은 Project 객체를 통해 알 수 있다 --> project.getName()
     * DefaultMutableTreeNode 객체에서 정의하면서 자동으로 입력된다.
     *
     */
    public static TreeModel createProjectTreeModel(Project project) {
        // the root node of the tree
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);

        // The visitor to traverse the Java hierarchy and to construct the tree
        final JavaElementVisitor visitor = new JavaElementVisitor() {
            // TODO: add member variables if necessary

            @Override
            public void visitPackage(PsiPackage pack) {
                // TODO: add a new node to the parent node, and traverse the content of the package
                System.out.println("3. " + pack.getName());
                for (PsiPackage tmp : pack.getSubPackages()) {
                    System.out.println("7. " + tmp.getName());
                }
            }

            @Override
            public void visitClass(PsiClass aClass) {
                // TODO: add a new node the parent node, and traverse the content of the class
                System.out.println("4. " + aClass.getName());
            }

            @Override
            public void visitMethod(PsiMethod method) {
                // TODO: add a new node to the parent node
                System.out.println("5. " + method.getName());
            }

            @Override
            public void visitField(PsiField field) {
                // TODO: add a new node to the parent node
                System.out.println("6. " + field.getName());
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
                    System.out.println("1. " + psiPackage.getQualifiedName());
                    rootPackages.add(psiPackage);
                }
                // Print 결과, 모두 null이라서 else 구문이 실행되는데, 네 가지 경우, (왜 네 가지인거야...?)
                // problem2/src/main: null -> edu
                // problem2/src/test: null -> edu
                // null -> META-INF
                // null
                else {
                    System.out.println("2. " + psiPackage.getName());
                    System.out.println("2. " + psiPackage.getQualifiedName());
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

        return rootPackages;
    }
}

