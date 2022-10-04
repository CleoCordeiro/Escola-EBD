import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/pessoa">
        <Translate contentKey="global.menu.entities.pessoa" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/responsavel">
        <Translate contentKey="global.menu.entities.responsavel" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/telefone">
        <Translate contentKey="global.menu.entities.telefone" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/endereco">
        <Translate contentKey="global.menu.entities.endereco" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/turma">
        <Translate contentKey="global.menu.entities.turma" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
