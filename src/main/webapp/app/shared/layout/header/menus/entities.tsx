import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/cise-authority">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.ciseAuthority" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/cise-service">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.ciseService" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/cise-rule-set">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.ciseRuleSet" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/cise-rule">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.ciseRule" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/cise-service-profile">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.ciseServiceProfile" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
