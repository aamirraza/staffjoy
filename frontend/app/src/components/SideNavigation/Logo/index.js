import React, { PropTypes } from 'react';
import { Link } from 'react-router';
import {
  COMPANY_BASE,
  getRoute,
} from 'constants/paths';

require('./navigation-logo.scss');
const imgUrl = require(
  '../../../../../resources/images/planner.png'
);

function NavigationLogo({ companyId }) {
  const route = getRoute(COMPANY_BASE, { companyId });
  return (
    <Link to={route} id="navigation-logo">
      <img role="presentation" alt="Planner  logo" src={imgUrl} />
    </Link>
  );
}

NavigationLogo.propTypes = {
  companyId: PropTypes.string.isRequired,
};

export default NavigationLogo;
