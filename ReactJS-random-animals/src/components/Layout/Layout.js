import Header from "../Header/Header";
import "./styled";
import { PageLayout } from "./styled";

function Layout ({children}) {

  return (
    <PageLayout>
      <Header />
      {children}
    </PageLayout>
  );
}

export default Layout;